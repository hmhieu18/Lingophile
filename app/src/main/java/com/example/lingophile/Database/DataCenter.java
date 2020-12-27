package com.example.lingophile.Database;

import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.User;
import com.google.firebase.auth.FirebaseAuth;

public class DataCenter {
    private FirebaseManagement firebaseManagement=FirebaseManagement.getInstance();
    public DataCenter() {
//        firebaseManagement.isLogin();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user = new User();

    private Lesson lesson = new Lesson();

    public Lesson getLesson() {return lesson; }
    public void setLesson(Lesson lesson) {this.lesson = lesson;}


    private static class SingletonHolder {
        private static final DataCenter INSTANCE = new DataCenter();
    }

    public static DataCenter getInstance() {
        return DataCenter.SingletonHolder.INSTANCE;
    }

}
