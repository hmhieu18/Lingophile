package com.example.lingophile.ViewModels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Helper.ReadDataListener;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.User;

import java.util.ArrayList;

public class UserInfo extends ViewModel {
    /*
    .....
     */
    private static FirebaseManagement management = FirebaseManagement.getInstance();
    private static ArrayList<Lesson> arrayListLesson = null;

    private UserInfo(){

    }
    private static class SingletonHolder {
        private static final UserInfo INSTANCE = new UserInfo();
    }

    public static UserInfo getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ArrayList<Lesson> getArrayListLesson(){
        return arrayListLesson;
    }

    public void requestArrayListLesson(String userID, final ReadDataListener mRead){
        Log.d("@@@", userID);
        management.getLessonListByUserID(userID, new ReadDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                arrayListLesson = DataCenter.getInstance().getLessonArrayList();
                Log.d("@@@", DataCenter.getInstance().getLessonArrayList().toString());
                mRead.onFinish();
            }

            @Override
            public void onFail() {

            }

            @Override
            public void updateUI() {

            }

            @Override
            public void onListenLessonSuccess(Lesson lesson) {

            }
        });
    }



}
