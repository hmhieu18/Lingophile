package com.example.lingophile.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseManagement firebase = FirebaseManagement.getInstance();
        firebase.requestUser("minhhieu2214@gmail.com");
    }
}