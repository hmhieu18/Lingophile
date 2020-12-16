package com.example.lingophile.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.R;

public class LoginActivity extends AppCompatActivity {
    private FirebaseManagement firebaseManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}