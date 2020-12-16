package com.example.lingophile.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManagement {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private static class SingletonHolder{
        private static final FirebaseManagement INSTANCE = new FirebaseManagement();
    }
    public static FirebaseManagement getInstance(){
        return SingletonHolder.INSTANCE;
    }
    public FirebaseManagement()
    {
        myRef = FirebaseDatabase.getInstance().getReference();
    }
    public DatabaseReference getDatabaseReference(){
        return myRef;
    }
}
