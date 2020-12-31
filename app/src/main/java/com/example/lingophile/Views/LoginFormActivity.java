package com.example.lingophile.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginFormActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginBtn;
    private FirebaseManagement firebaseManagement = FirebaseManagement.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        initComponent();
    }

    private void initComponent() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.login_btn2);
        loginBtn.setOnClickListener(loginClick);
    }

    View.OnClickListener loginClick = new View.OnClickListener() {
        public void onClick(View v) {
            String _email = emailEditText.getText().toString();
            String _password = passwordEditText.getText().toString();
            if (!_email.equals("") && !_password.equals(""))
                firebaseManagement.login(LoginFormActivity.this, _email, _password);
            else
            {
                Toast.makeText(LoginFormActivity.this, "Please check your input again", Toast.LENGTH_SHORT).show();
            }
        }
    };
}