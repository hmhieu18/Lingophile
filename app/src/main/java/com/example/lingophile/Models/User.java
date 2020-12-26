package com.example.lingophile.Models;

import androidx.arch.core.executor.DefaultTaskExecutor;

import com.example.lingophile.Database.FirebaseManagement;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class User {
    /*
    Store data and getting setting methods
    isMatch ----> match password with encryption method

     */
    private String _email;
    private Float _rating;
    private String _username;
    private String _userID;
    private ArrayList<Lesson> _list;


    public User(){
        _email = "";
        _rating = Float.valueOf(0);
        _userID = "";
        _username = "";
        _list = new ArrayList<>();
    }

    public User(DataSnapshot snapshot){
        _email = snapshot.child("email").getValue().toString();
        _rating = Float.valueOf(snapshot.child("rating").getValue().toString());
        _userID = snapshot.child("userID").getValue().toString();
        _username = snapshot.child("username").getValue().toString();
        for (DataSnapshot lesson : snapshot.child("lessons_list").getChildren()){
            _list.add(FirebaseManagement.getInstance().requestLesson(lesson.getValue().toString()));
        }
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public Float getRating() {
        return _rating;
    }

    public void setRating(Float rating) {
        this._rating = rating;
    }

    public String getUsername() {
        return _username;
    }

    public void setUsername(String username) {
        this._username = username;
    }

    public String getUserID() {
        return _userID;
    }

    public void setUserID(String userID) {
        this._userID = userID;
    }

    public ArrayList<Lesson> getList() {
        return _list;
    }

    public void setList(ArrayList<Lesson> list) {
        this._list = _list;
    }

    public void addLesson(Lesson lesson){
        _list.add(lesson);
    }
}
