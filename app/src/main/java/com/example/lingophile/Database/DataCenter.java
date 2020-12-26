package com.example.lingophile.Database;

import com.example.lingophile.Models.User;
import com.google.firebase.auth.FirebaseAuth;

public class DataCenter {
    private FirebaseManagement firebaseManagement=FirebaseManagement.getInstance();
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

}
