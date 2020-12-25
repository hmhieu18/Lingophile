package com.example.lingophile.Models;

import java.util.ArrayList;

public class User {
    public User(String userID, String username, String email, float rating, ArrayList<Lesson> lessonArrayList) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.rating = rating;
        this.lessonArrayList = lessonArrayList;
    }

    public User() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    private String userID, username, email;
    private float rating;
    private ArrayList<Lesson> lessonArrayList = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ArrayList<Lesson> getLessonArrayList() {
        return lessonArrayList;
    }

    public void setLessonArrayList(ArrayList<Lesson> lessonArrayList) {
        this.lessonArrayList = lessonArrayList;
    }


    public User(String userID, String username, String email) {
        this.username = username;
        this.email = email;
        this.userID = userID;
        this.rating = 5;
        this.lessonArrayList = new ArrayList<>();
    }


}
