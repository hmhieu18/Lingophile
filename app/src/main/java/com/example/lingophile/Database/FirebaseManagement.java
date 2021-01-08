package com.example.lingophile.Database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lingophile.Helper.ReadDataListener;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.LessonIDSchedule;
import com.example.lingophile.Models.Schedule;
import com.example.lingophile.Models.User;
import com.example.lingophile.Views.MainActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class FirebaseManagement {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private DataCenter dataCenter = DataCenter.getInstance();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    public StorageReference getmStorageRef() {
        return mStorageRef;
    }

    public void setmStorageRef(StorageReference mStorageRef) {
        this.mStorageRef = mStorageRef;
    }


    public static void getLessonByID() {
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public void addLessonByID(Lesson lesson) {
        myRef.child("lessons_list").child(lesson.getLessonID()).setValue(lesson);
    }

    public void updateUserLessonList() {
        myRef.child("users").child(mAuth.getUid()).child("lessons_list").setValue(dataCenter.user.getLessonIDArrayList());
    }

    private static class SingletonHolder {
        private static final FirebaseManagement INSTANCE = new FirebaseManagement();
    }

    public static FirebaseManagement getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public FirebaseManagement() {
        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public DatabaseReference getDatabaseReference() {
        return myRef;
    }

    public void login(final Activity activity, String email, String password) {
        FirebaseManagement.getInstance().mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            loadUser(new ReadDataListener() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onFinish() {
                                    SharedPreferences sharedPreferences = activity.getSharedPreferences("ID", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("UserID", DataCenter.getInstance().user.getUserID());
                                    editor.apply();
                                    Intent myIntent = new Intent(activity, MainActivity.class);
                                    activity.startActivity(myIntent);

                                }

                                @Override
                                public void onFail() {

                                }

                                @Override
                                public void updateUI() {

                                }

                                @Override
                                public void onListenLessonSuccess(Lesson lesson) {

                                }
                            });

                        } else {
                            Toast.makeText(activity, "Log In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void register(final Activity activity, String email, String password, String usename) {
        FirebaseManagement.getInstance().mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            User user = new User(firebaseUser.getUid(), "", firebaseUser.getEmail());
                            dataCenter.setUser(user);
                            writeNewUser(user);
                            Intent myIntent = new Intent(activity, MainActivity.class);
                            activity.startActivity(myIntent);
                        } else {
                            Toast.makeText(activity, "Sign up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void writeNewUser(User user) {
        myRef.child("users").child(user.getUserID()).setValue(user);
    }

    public void updateUsername(String username) {
        myRef.child("users").child(dataCenter.user.getUserID()).child("username").setValue(username);
    }

    public boolean isLogin() {
        return mAuth.getCurrentUser() != null;
    }

    public void loadUser(final ReadDataListener mRead) {
        mRead.onStart();
        DatabaseReference ref = myRef.child("users").child(mAuth.getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataCenter.user = dataSnapshot.getValue(User.class);
                mRead.onFinish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                mRead.onFail();
            }
        });
    }

    public void updateLessonToDatabase() {
        myRef.child("users").child(mAuth.getUid()).child("lessons_list").setValue(dataCenter.user.getLessonIDArrayList());
    }

    public void requestLessonSearch(final String search, final ReadDataListener mRead) {
        mRead.onStart();
        DatabaseReference ref = myRef.child("lessons_list");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<Pair<Float, Lesson>> arrayList = new ArrayList<>();
                    for (DataSnapshot lesson : snapshot.getChildren()) {
                        if (lesson.child("_private").getValue(Boolean.TYPE))
                            continue;
                        String title = lesson.child("title").getValue().toString();
                        String des = lesson.child("description").getValue().toString();
                        float match_title = matching(search, title);
                        float match_des = matching(search, des);
                        float rate = Math.max(match_title, match_des);
                        Pair<Float, Lesson> add = new Pair<>(Float.valueOf(rate), lesson.getValue(Lesson.class));
                        arrayList.add(add);
                    }
                    arrayList.sort(new Comparator<Pair<Float, Lesson>>() {
                        @Override
                        public int compare(Pair<Float, Lesson> floatLessonPair, Pair<Float, Lesson> t1) {
                            if (floatLessonPair.first > t1.first)
                                return -1;
                            if (floatLessonPair.first < t1.first)
                                return 1;
                            return 0;
                        }
                    });
                    ArrayList<Lesson> update = new ArrayList<>();
                    for (Pair<Float, Lesson> item : arrayList) {
                        if (item.first > 0.5)
                            update.add(item.second);
                    }
                    DataCenter.getInstance().setLessonArrayList(update);
                }
                mRead.onFinish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mRead.onFail();
            }
        });
    }

    public void requestUserSearch(final String request, final ReadDataListener mRead) {
        mRead.onStart();
        DatabaseReference ref = myRef.child("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<Pair<Float, User>> arrayList = new ArrayList<>();
                    for (DataSnapshot user : snapshot.getChildren()) {
                        String email = user.child("email").getValue().toString();
                        String uid = user.child("userID").getValue().toString();
                        String name = user.child("username").getValue().toString();
                        float match_email = matching(request, email.substring(0, email.indexOf('@')));
                        float match_id = matching(request, uid);
                        float match_name = matching(request, name);
                        float rate = Math.max(Math.max(match_email, match_id), match_name);
                        Pair<Float, User> add = new Pair<>(Float.valueOf(rate), user.getValue(User.class));
                        arrayList.add(add);
                    }
                    ArrayList<User> update = new ArrayList<>();
                    for (Pair<Float, User> item : arrayList) {
                        if (item.first > 0.5)
                            update.add(item.second);
                    }
                    DataCenter.getInstance().setUserArrayList(update);
                }
                mRead.onFinish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mRead.onFail();
            }
        });
    }


    private float matching(String x, String y) {
        if (Math.min(x.length(), y.length()) == 0)
            return 0;
        int rows = x.length() + 1;
        int cols = y.length() + 1;
        int[][] distance = new int[rows][cols];
        for (int i = 1; i < rows; i++)
            for (int j = 1; j < cols; j++) {
                distance[i][0] = i;
                distance[0][j] = j;
            }
        for (int col = 1; col < cols; col++)
            for (int row = 1; row < rows; row++) {
                int cost;
                if (x.charAt(row - 1) == y.charAt(col - 1))
                    cost = 0;
                else
                    cost = 2;
                distance[row][col] = Math.min(Math.min(distance[row - 1][col] + 1, distance[row][col - 1] + 1), distance[row - 1][col - 1] + cost);
            }
        return (float) ((x.length() + y.length() - distance[rows - 1][cols - 1]) * 1.0 / (x.length() + y.length()));
    }

    public void loadUserLessonIDList(final ReadDataListener mRead) {
        myRef.child("users").child(mAuth.getCurrentUser().getUid()).child("lessons_list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<LessonIDSchedule> tempLessonList = new ArrayList<>();
                final ArrayList<Lesson> lessonArrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.v("@@@", "" + dataSnapshot1.getKey()); //displays the key for the node
                    String tempString = dataSnapshot1.child("lessonID").getValue(String.class);
                    Schedule tempSchedule = dataSnapshot1.child("schedule").getValue(Schedule.class);
                    LessonIDSchedule temp = new LessonIDSchedule(tempString, tempSchedule);   //gives the value for given keyname
                    System.out.println(temp.getLessonID());
                    tempLessonList.add(temp);
                    loadLessonByID(tempString, new ReadDataListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFinish() {

                        }

                        @Override
                        public void onFail() {

                        }

                        @Override
                        public void updateUI() {

                        }

                        @Override
                        public void onListenLessonSuccess(Lesson lesson) {
                            lessonArrayList.add(lesson);
                            dataCenter.setThisUserLessonArrayList(lessonArrayList);
                            mRead.updateUI();
                        }
                    });
                }
                dataCenter.user.setLessonIDArrayList(tempLessonList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void loadLessonByID(String lessonID, final ReadDataListener mRead) {
//        DatabaseReference ref = firebaseManagement.getDatabaseReference().;
        myRef.child("lessons_list").child(lessonID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mRead.onListenLessonSuccess(dataSnapshot.getValue(Lesson.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void getLessonListByUserID(String userID, final ReadDataListener mRead) {
        mRead.onStart();
        DatabaseReference ref = myRef.child("users").child(userID).child("lessons_list");
        dataCenter.setLessonArrayList(new ArrayList<Lesson>());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        loadLessonByID(data.child("lessonID").getValue(String.class), new ReadDataListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onFinish() {

                            }

                            @Override
                            public void onFail() {

                            }

                            @Override
                            public void updateUI() {

                            }

                            @Override
                            public void onListenLessonSuccess(Lesson lesson) {
                                dataCenter.addLesson(lesson);
                                mRead.onFinish();
                                //Log.d("@@@", dataCenter.getLessonArrayList().toString());
                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getAvatar() throws IOException {
//        File localFile = File.createTempFile("images", "jpg");
//        mStorageRef.getFile(localFile)
//                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                        // Successfully downloaded data to local file
//                        // ...
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle failed download
//                // ...
//            }
//        });
        // Reference to an image file in Cloud Storage

    }

    public void uploadAvatar(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mStorageRef.child("avatar").child(dataCenter.user.getUserID()).putBytes(data);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return mStorageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                }
            }
        });
    }

}
