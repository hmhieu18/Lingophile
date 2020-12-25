package com.example.lingophile.Database;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lingophile.Models.User;
import com.example.lingophile.Views.LoginActivity;
import com.example.lingophile.Views.LoginFormActivity;
import com.example.lingophile.Views.MainActivity;
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

import java.util.concurrent.Executor;

public class FirebaseManagement {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    public static void getLessonByID() {
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
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
                            loadUser();
                            Intent myIntent = new Intent(activity, MainActivity.class);
                            activity.startActivity(myIntent);
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
                            DataCenter.setUser(user);
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

    public boolean isLogin() {
        if (mAuth.getCurrentUser() != null) {
            loadUser();
            return true;
        }
        return false;
    }

    private void loadUser() {
        DatabaseReference ref = myRef.child("users").child(mAuth.getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataCenter.user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void saveUserNewPlantToDatabase() {
        myRef.child("users").child(mAuth.getUid()).child("lessons_list").setValue(DataCenter.user.getLessonArrayList());
    }
}
