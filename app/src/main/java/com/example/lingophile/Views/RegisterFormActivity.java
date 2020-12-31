package com.example.lingophile.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Models.User;
import com.example.lingophile.R;

public class RegisterFormActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, password2EditText, usernameEditText;
    private Button registerBtn;
    private FirebaseManagement firebaseManagement = FirebaseManagement.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        initComponent();
    }

    private void initComponent() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEdittext);
        usernameEditText = findViewById(R.id.usernameEditText);

        password2EditText = findViewById(R.id.password2Edittext);
        registerBtn = findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(registerClick);
    }

    View.OnClickListener registerClick = new View.OnClickListener() {
        public void onClick(View v) {
            if (!emailEditText.getText().toString().equals("") && !passwordEditText.getText().toString().equals("") && !usernameEditText.getText().toString().equals("")) {
                firebaseManagement.register(RegisterFormActivity.this, emailEditText.getText().toString(), passwordEditText.getText().toString(), usernameEditText.getText().toString());
            }
            else
            {
                Toast.makeText(RegisterFormActivity.this, "Please check your input again", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean checkValidInput() {
//        if (isValidEmail(emailEditText.getText().toString()) && passwordEditText.getText().toString().equals(password2EditText.getText().toString())) {
//            return true;
//        }
//        return false;
        return true;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
//    private void writeNewUser(String userID, String name, String email) {
//        User user = new User(userID, name, email);
//        DataCenter.setUser(user);
//        firebaseManagement.writeNewUser(user);
//    }
}