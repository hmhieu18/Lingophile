package com.example.lingophile.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Helper.ReadDataListener;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.R;

public class LoginFormActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    View.OnClickListener loginClick = new View.OnClickListener() {
        public void onClick(View v) {
            String _email = emailEditText.getText().toString();
            String _password = passwordEditText.getText().toString();
            if (!_email.equals("") && !_password.equals(""))
                firebaseManagement.login(LoginFormActivity.this, _email, _password);
            else {
                Toast.makeText(LoginFormActivity.this, "Please check your input again", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private FirebaseManagement firebaseManagement = FirebaseManagement.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        initComponent();
    }

    View.OnClickListener resetClick = new View.OnClickListener() {
        public void onClick(View v) {
            String _email = emailEditText.getText().toString();
//            String _password = passwordEditText.getText().toString();
            if (!_email.equals(""))
                firebaseManagement.resetPassword(new ReadDataListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(LoginFormActivity.this, "Reset email sent", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(LoginFormActivity.this, "Your email has not linked to any account yet", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void updateUI() {

                    }

                    @Override
                    public void onListenLessonSuccess(Lesson lesson) {

                    }
                }, _email);
            else {
                Toast.makeText(LoginFormActivity.this, "Please fill in your email", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Button loginBtn, resetPasswordBtn;

    private void initComponent() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        resetPasswordBtn = findViewById(R.id.resetPasswordBtn);

        loginBtn = findViewById(R.id.login_btn2);
        loginBtn.setOnClickListener(loginClick);
        resetPasswordBtn.setOnClickListener(resetClick);
    }

}