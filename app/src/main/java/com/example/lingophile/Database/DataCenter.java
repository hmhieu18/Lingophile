package com.example.lingophile.Database;

import com.example.lingophile.Models.User;

public class DataCenter {
    private static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        DataCenter.user = user;
    }

    public static User user = new User();
}
