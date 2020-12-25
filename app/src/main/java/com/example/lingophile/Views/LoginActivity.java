package com.example.lingophile.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Models.User;
import com.example.lingophile.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn, registerBtn;
    private FirebaseManagement firebaseManagement = FirebaseManagement.getInstance();

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseManagement.isLogin()) {
            onAuthSuccess();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponent();
        mRequestPermission();

    }

    private void onAuthSuccess() {
//        String username = usernameFromEmail(user.getEmail());
//        User mUser = new User(user.getUid(), "username", user.getEmail());
//        DataCenter.user = mUser;
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void mRequestPermission() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.VIBRATE,
                Manifest.permission.INTERNET
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void initComponent() {
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(loginClick);
        registerBtn = findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(registerClick);
    }

    View.OnClickListener loginClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent myIntent = new Intent(LoginActivity.this, LoginFormActivity.class);
            LoginActivity.this.startActivity(myIntent);
        }
    };
    View.OnClickListener registerClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent myIntent = new Intent(LoginActivity.this, RegisterFormActivity.class);
            LoginActivity.this.startActivity(myIntent);
        }
    };
}