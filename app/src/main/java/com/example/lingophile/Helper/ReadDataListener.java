package com.example.lingophile.Helper;

import androidx.annotation.Nullable;

import com.example.lingophile.Models.Lesson;

public interface ReadDataListener {
    void onStart();
    void onFinish();
    void onFail();
    void updateUI();
    void onListenLessonSuccess(Lesson lesson);
}
