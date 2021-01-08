package com.example.lingophile.Models;

import com.example.lingophile.Database.FirebaseManagement;

import java.io.Serializable;
import java.util.ArrayList;

public class Lesson implements Serializable {
    private String lessonID = Long.toString(System.currentTimeMillis() / 1000);
    private int numberOfCard;
    private String authorName;
    private float rating = 5;
    private float percentage = 0;
    private boolean _private = false;
    private String description;
    private String title;
    private ArrayList<FlashCard> flashCardArrayList = new ArrayList<>();

    public boolean is_private() {
        return _private;
    }

    public void set_private(boolean _private) {
        this._private = _private;
    }


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


/*
public class Lesson {
    public String name;
    public Schedule learningSchedule;

    public Lesson(String _name) {
        this.name = _name;
    }

    public Lesson() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }

    public static class Schedule {
        public ArrayList<Integer> dayOfWeek;
        public int hour, min;

        public Schedule(ArrayList<Integer> dayOfWeek, int hour, int min, long eventID) {
            this.dayOfWeek = dayOfWeek;
            this.hour = hour;
            this.min = min;
            this.eventID = eventID;
        }

        public long eventID;

        public Schedule(ArrayList<Integer> dayOfWeek, int hour, int min) {
            this.dayOfWeek = dayOfWeek;
            this.hour = hour;
            this.min = min;
        }

        public Schedule() {
        }
    }
}
 */