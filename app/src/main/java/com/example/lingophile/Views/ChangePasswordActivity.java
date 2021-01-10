package com.example.lingophile.Views;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lingophile.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    private TextView _oldpass;
    private TextView _newpass;
    private TextView _confirm;
    private String _old, _new, _con;
    private Button _changePassButton;
    private FirebaseUser user;
    private View baseView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initComponents();
        Finish();
    }

    private void Finish() {
        _changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _old = _oldpass.getText().toString().trim();
                _new = _newpass.getText().toString().trim();
                _con = _confirm.getText().toString().trim();
                checkPasswordCondition();
            }
        });
    }

    private void checkPasswordCondition() {
        if (TextUtils.isEmpty(_old) || TextUtils.isEmpty(_new) || TextUtils.isEmpty(_con)) {
            Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_LONG).show();
        } else {
            if (_new.length() < 6) {
                Toast.makeText(getApplicationContext(), "Password length must be at least 6 characters", Toast.LENGTH_LONG).show();
            } else {
                if (_new.equals(_con) == false) {
                    Toast.makeText(getApplicationContext(), "New password does't match the confirm password", Toast.LENGTH_LONG).show();
                } else {
                    if (_new.equals(_old) == true) {
                        Toast.makeText(getApplicationContext(), "New password must not be the same as password", Toast.LENGTH_LONG).show();
                    } else
                        updatePassword(_old, _new);
                }
            }
        }
    }

    private void updatePassword(String old, final String aNew) {
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), old);
        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                user.updatePassword(aNew).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Password Changed. Please Login Again", Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        //Make sure the user cannot go back
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void initComponents() {
        _oldpass = findViewById(R.id.change_pass_old);
        _newpass = findViewById(R.id.change_pass_new);
        _confirm = findViewById(R.id.change_pass_confirm);
        _changePassButton = findViewById(R.id.change_pass_button);
        user = FirebaseAuth.getInstance().getCurrentUser();
        _oldpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        _newpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        _confirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
}