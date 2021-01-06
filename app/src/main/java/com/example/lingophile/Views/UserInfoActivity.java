package com.example.lingophile.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lingophile.Adapter.MyLessonListAdapter;
import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Helper.ReadDataListener;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.User;
import com.example.lingophile.R;
import com.example.lingophile.ViewModels.UserInfo;

import java.util.ArrayList;

public class UserInfoActivity extends AppCompatActivity {
    private DataCenter dataCenter = DataCenter.getInstance();
    private FirebaseManagement management = FirebaseManagement.getInstance();
    private User author;
    private ImageView imageView;

    private TextView fullname, email;
    private RatingBar ratingBar;
    private ListView authorList;
    private MyLessonListAdapter myLessonListAdapter;
    private ArrayList<Lesson> lessonArrayList;
    private static UserInfo userInfo = UserInfo.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                author = null;
            } else {
                author = (User) extras.getSerializable("author");
            }
        } else {
            author = (User) savedInstanceState.getSerializable("author");
        }
//        Log.d("lesson ID", lessonID);
//        lesson = dataCenter.getLessonByID(lessonID);
        setContentView(R.layout.authors_info);
        initComponent();
    }

    private void initComponent() {
        imageView = (ImageView) findViewById(R.id.author_image);
        fullname = (TextView) findViewById(R.id.author_info_fullname);
        email = (TextView) findViewById(R.id.author_info_email);
        ratingBar = (RatingBar) findViewById(R.id.rating);
        authorList = (ListView) findViewById(R.id.author_info_list);
        toUI();
    }

    @SuppressLint("SetTextI18n")
    private void toUI(){
        fullname.setText(author.getUsername());
        email.setText(author.getEmail());
        ratingBar.setRating(author.getRating());
        userInfo.requestArrayListLesson(author.getUserID(), new ReadDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                lessonArrayList = userInfo.getArrayListLesson();
                myLessonListAdapter = new MyLessonListAdapter(getBaseContext(), R.layout.lesson_item, lessonArrayList);
                //Log.d("@@@", lessonArrayList.toString());
                authorList.setAdapter(myLessonListAdapter);
                authorList.setOnItemClickListener(listViewItemOnClick);
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

    private ListView.OnItemClickListener listViewItemOnClick = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent myIntent = new Intent(UserInfoActivity.this, LessonViewActivity.class);
            myIntent.putExtra("lesson", lessonArrayList.get(position));
            UserInfoActivity.this.startActivity(myIntent);
        }
    };
}
