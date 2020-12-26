package com.example.lingophile.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirebaseManagement {
    private FirebaseDatabase database;
    private static class SingletonHolder{
        private static final FirebaseManagement INSTANCE = new FirebaseManagement();
    }
    public static FirebaseManagement getInstance(){
        return SingletonHolder.INSTANCE;
    }
    public FirebaseManagement()
    {
        database = FirebaseDatabase.getInstance();
    }

    public User requestUser(final String username){
        DatabaseReference myRef = database.getReference();
        Query query = myRef.child("users").orderByChild("email").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snap : snapshot.getChildren()){
                        User cur = new User(snap);
                        Log.d("@@@", cur.getEmail());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return null;
    }

    public Lesson requestLesson(String lessonID){

        return null;
    }

}
