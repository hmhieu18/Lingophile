package com.example.lingophile.Models;

import java.util.ArrayList;

public class User {

    public String username;
    public String email;
    public ArrayList<Lesson> userLessons=new ArrayList<>();
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
