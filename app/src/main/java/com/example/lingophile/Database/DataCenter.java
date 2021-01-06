package com.example.lingophile.Database;

import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.User;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;

public class DataCenter {
    private FirebaseManagement firebaseManagement = FirebaseManagement.getInstance();
    private TranslatorOptions options =
            new TranslatorOptions.Builder()
                    .setSourceLanguage(TranslateLanguage.ENGLISH)
                    .setTargetLanguage(TranslateLanguage.VIETNAMESE)
                    .build();
    private final Translator englishToVietnameseTranslator =
            Translation.getClient(options);

    public Translator getEnglishToVietnameseTranslator() {
        return englishToVietnameseTranslator;
    }

    public ArrayList<Lesson> getLessonArrayList() {
        return lessonArrayList;
    }
    public void addLesson(Lesson lesson){
        this.lessonArrayList.add(lesson);
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

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public void setUserArrayList(ArrayList<User> arrayList) {
        this.userArrayList = arrayList;
    }

}
