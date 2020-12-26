package com.example.lingophile.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lingophile.Adapter.SmallFlashCardListAdapter;
import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.R;

public class LessonViewActivity extends AppCompatActivity {
    private DataCenter dataCenter = DataCenter.getInstance();
    private Lesson lesson;
    private RatingBar ratingbar;
    private TextView lessonTitleTextView, topicTextView, authorTextView, numberOfCardTextView;
    Button flashcardBtn, editBtn, testBtn, addToMyList;
    private ListView flashcardListView;
    private SmallFlashCardListAdapter smallFlashCardListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                position = 0;
            } else {
                position = extras.getInt("lesson");
            }
        } else {
            position = savedInstanceState.getInt("lesson");
        }
        lesson = dataCenter.user.getLessonArrayList().get(position);
        setContentView(R.layout.activity_lesson_view);
        initComponent();
    }

    private void initComponent() {
        flashcardBtn = findViewById(R.id.flashCardBtn);
        editBtn = findViewById(R.id.EditBtn);
        testBtn = findViewById(R.id.TestBtn);
        addToMyList = findViewById(R.id.addToMyListBtn);
        ratingbar = findViewById(R.id.rating);
        lessonTitleTextView = findViewById(R.id.LessonName);
        authorTextView = findViewById(R.id.authorName);
        topicTextView = findViewById(R.id.topic);
        numberOfCardTextView = findViewById(R.id.numberOfCard);

        flashcardListView = findViewById(R.id.flashcardListView);
        smallFlashCardListAdapter = new SmallFlashCardListAdapter(this, R.layout.small_flashcard_item, lesson.getFlashCardArrayList());
        flashcardListView.setAdapter(smallFlashCardListAdapter);
        flashcardListView.setOnItemClickListener(listViewItemOnClick);
        lessonToUI();
    }

    @SuppressLint("SetTextI18n")
    private void lessonToUI() {
        lessonTitleTextView.setText(lesson.getTitle());
        authorTextView.setText(lesson.getAuthorName());
        topicTextView.setText(lesson.getDescription());
        numberOfCardTextView.setText(Integer.toString(lesson.getFlashCardArrayList().size()));
        ratingbar.setRating(lesson.getRating());
    }

    private ListView.OnItemClickListener listViewItemOnClick = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            openFragment(PlantDetailsFragment.newInstance(AppData.user.userPlants.get(position), position, false));
        }
    };
}