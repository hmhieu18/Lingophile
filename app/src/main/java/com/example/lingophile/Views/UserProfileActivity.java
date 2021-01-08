package com.example.lingophile.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lingophile.Models.User;
import com.example.lingophile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileActivity extends AppCompatActivity {

    private TextView _username;
    private TextView _email;
    private RatingBar _rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initComponents();
    }

    private void initComponents() {
        _email = findViewById(R.id.user_info_email);
        _username = findViewById(R.id.user_info_username);
//        String _emailInfo;
        _rating =findViewById(R.id.user_info_rating);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){ _rating.setRating(4);
           _username.setText(user.getEmail());
           _email.setText(user.getEmail());
        }
//        _emailInfo = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//        _email.setText(_emailInfo);
//        String _usernameInfo;
//        _usernameInfo = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//        _username.setText(_usernameInfo);
    }

}