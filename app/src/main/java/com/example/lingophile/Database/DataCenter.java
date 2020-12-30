package com.example.lingophile.Database;

import android.os.Parcelable;
import android.widget.ArrayAdapter;

import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DataCenter {
    private FirebaseManagement firebaseManagement = FirebaseManagement.getInstance();

    public ArrayList<Lesson> getLessonArrayList() {
        return lessonArrayList;
    }

    public void setLessonArrayList(ArrayList<Lesson> lessonArrayList) {
        this.lessonArrayList = lessonArrayList;
    }

    public Lesson getLessonByID(String ID) {
        for (Lesson lesson : lessonArrayList) {
            if (lesson.getLessonID() == ID) {
                return lesson;
            }
        }
        return null;
    }

    private ArrayList<Lesson> lessonArrayList = new ArrayList<>();

    public ArrayList<Lesson> getThisUserLessonArrayList() {
        return thisUserLessonArrayList;
    }

    public void setThisUserLessonArrayList(ArrayList<Lesson> thisUserLessonArrayList) {
        this.thisUserLessonArrayList = thisUserLessonArrayList;
    }

    private ArrayList<Lesson> thisUserLessonArrayList = new ArrayList<>();

    public DataCenter() {
//        firebaseManagement.isLogin();
    }

    private User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User user = new User();

    private static class SingletonHolder {
        private static final DataCenter INSTANCE = new DataCenter();
    }

    public static DataCenter getInstance() {
        return DataCenter.SingletonHolder.INSTANCE;
    }


    private ArrayList<User> userArrayList = new ArrayList<>();
    public ArrayList<User> getUserArrayList(){
        return userArrayList;
    }
    public void setUserArrayList(ArrayList<User> arrayList){
        this.userArrayList = arrayList;
    }

}
