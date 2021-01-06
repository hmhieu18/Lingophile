package com.example.lingophile.Models;

import java.util.ArrayList;
public class User {
    /*
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
    */
    private String userID, username, email;
    private float rating;

    public ArrayList<LessonIDSchedule> getLessonIDArrayList() {
        return lessonIDArrayList;
    }

    public void setLessonIDArrayList(ArrayList<LessonIDSchedule> lessonIDArrayList) {
        this.lessonIDArrayList = lessonIDArrayList;
    }

    public ArrayList<LessonIDSchedule> lessonIDArrayList = new ArrayList<>();

    public User(String userID, String username, String email, float rating, ArrayList<LessonIDSchedule> lessonIDArrayList) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.rating = rating;
        this.lessonIDArrayList = lessonIDArrayList;
    }

    public Schedule getScheduleByLessonID(String lessonID) {
        for (LessonIDSchedule stringSchedulePair : lessonIDArrayList) {
            if (stringSchedulePair.getLessonID().equals(lessonID))
                return stringSchedulePair.getSchedule();
        }
        return null;
    }

    public boolean containLessonID(String lessonID) {
        for (LessonIDSchedule stringSchedulePair : lessonIDArrayList) {
            if (stringSchedulePair.getLessonID().equals(lessonID))
                return true;
        }
        return false;
    }

    public void removeLessonIDScheduleByID(String lessonID) {
        for (LessonIDSchedule lessonIDSchedule : lessonIDArrayList) {
            if (lessonIDSchedule.getLessonID().equals(lessonID))
                lessonIDArrayList.remove(lessonIDSchedule);
        }
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
