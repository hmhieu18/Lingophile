package com.example.lingophile.Views;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Helper.ReadDataListener;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.R;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn, registerBtn;
    private FirebaseManagement firebaseManagement = FirebaseManagement.getInstance();

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseManagement.isLogin()) {
            firebaseManagement.loadUser(
                    new ReadDataListener() {
                        ProgressDialog dialog;

                        @Override
                        public void onStart() {
                            dialog = new ProgressDialog(LoginActivity.this);
                            dialog.setMessage("Please wait...");
                            dialog.show();
                        }

                        @Override
                        public void onFinish() {
                            if (dialog.isShowing())
                                dialog.dismiss();
                            onAuthSuccess();
                        }

                        @Override
                        public void onFail() {
                            if (dialog.isShowing())
                                dialog.dismiss();
                        }

                        @Override
                        public void updateUI() {

                        }

                        @Override
                        public void onListenLessonSuccess(Lesson lesson) {

                        }
                    });
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
//
//    void abc()
//    {
//        arraylist<ArrayList<lesson>>
//        for(lessonid in lessonidarraylis)
//        {
//            lesson=findlessonbyid()
//            for(day in lessonid.schedule.dayofweek)
//            {
//
//            }
//        }
//    }

}