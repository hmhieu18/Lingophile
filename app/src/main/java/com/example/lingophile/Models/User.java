package com.example.lingophile.Models;

import android.util.Pair;

import java.util.ArrayList;

public class User {

    private String userID, username, email;
    private float rating;

    public ArrayList<LessonIDSchedule> getLessonIDArrayList() {
        return lessonIDArrayList;
    }

    public void setLessonIDArrayList(ArrayList<LessonIDSchedule> lessonIDArrayList) {
        this.lessonIDArrayList = lessonIDArrayList;
    }

    private ArrayList<LessonIDSchedule> lessonIDArrayList = new ArrayList<>();

    public User(String userID, String username, String email, float rating, ArrayList<LessonIDSchedule> lessonIDArrayList) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.rating = rating;
        this.lessonIDArrayList = lessonIDArrayList;
    }

    public Schedule getScheduleByLessonID(String lessonID) {
        for (LessonIDSchedule stringSchedulePair : lessonIDArrayList) {
            if (stringSchedulePair.getLessonID() == lessonID)
                return stringSchedulePair.getSchedule();
        }
        return null;
    }

    public void setScheduleByLessonID(String lessonID, Schedule schedule) {
        for (LessonIDSchedule stringSchedulePair : lessonIDArrayList) {
            if (stringSchedulePair.getLessonID().equals(lessonID)) {
                stringSchedulePair.setSchedule(schedule);
                return;
            }
        }
        lessonIDArrayList.add(new LessonIDSchedule(lessonID, schedule));
    }

    public User() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


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


    public User(String userID, String username, String email) {
        this.username = username;
        this.email = email;
        this.userID = userID;
        this.rating = 5;
        this.lessonIDArrayList = new ArrayList<>();
    }


}
