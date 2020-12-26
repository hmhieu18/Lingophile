package com.example.lingophile.Models;

import com.example.lingophile.Database.FirebaseManagement;

import java.io.Serializable;
import java.util.ArrayList;

public class Lesson implements Serializable {
    String lessonID = Long.toString(System.currentTimeMillis() / 1000);
    int numberOfCard;
    String authorName;
    float rating = 5;
    float percentage = 0;
    String description;
    String title;
    ArrayList<FlashCard> flashCardArrayList = new ArrayList<>();

    public Lesson(String authorName, String description, String title, ArrayList<FlashCard> flashCardArrayList) {
        this.authorName = authorName;
        this.description = description;
        this.title = title;
        this.flashCardArrayList = flashCardArrayList;
    }

    public Lesson(String authorName, String title, ArrayList<FlashCard> flashCardArrayList) {
        this.authorName = authorName;
        this.title = title;
        this.flashCardArrayList = flashCardArrayList;
    }


    public Lesson(String lessonID) {
        FirebaseManagement.getLessonByID();
    }

    public Lesson() {
        lessonID = "";
        numberOfCard = 0;
        authorName = "";
        description = "";
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
