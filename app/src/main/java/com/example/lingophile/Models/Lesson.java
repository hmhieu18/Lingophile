package com.example.lingophile.Models;

import android.os.Parcelable;

import com.example.lingophile.Database.FirebaseManagement;

import java.io.Serializable;
import java.util.ArrayList;

public class Lesson implements Serializable {
    String lessonID;
    int numberOfCard;
    String authorName;
    float rating;
    float percentage;
    String topic;
    String title;
    ArrayList<FlashCard> flashCardArrayList = new ArrayList<>();

    public Lesson(String authorName, String title, ArrayList<FlashCard> flashCardArrayList) {
        this.authorName = authorName;
        this.title = title;
        this.flashCardArrayList = flashCardArrayList;
    }

    public Schedule getLearningSchedule() {
        return learningSchedule;
    }

    public void setLearningSchedule(Schedule learningSchedule) {
        this.learningSchedule = learningSchedule;
    }

    public Schedule learningSchedule=new Schedule();

    public Lesson(String lessonID) {
        FirebaseManagement.getLessonByID();
    }

    public Lesson() {
        lessonID = "";
        numberOfCard = 0;
        authorName = "";
        topic = "";
        title = "";
        rating = 5;
        percentage = 0;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    public int getNumberOfCard() {
        return numberOfCard;
    }

    public void setNumberOfCard(int numberOfCard) {
        this.numberOfCard = numberOfCard;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<FlashCard> getFlashCardArrayList() {
        return flashCardArrayList;
    }

    public void setFlashCardArrayList(ArrayList<FlashCard> flashCardArrayList) {
        this.flashCardArrayList = flashCardArrayList;
    }
}
