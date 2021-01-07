package com.example.lingophile.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lingophile.Adapter.SmallFlashCardListAdapter;
import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.R;

public class LessonViewActivity extends AppCompatActivity implements StarRatingDialog.RatingDialogListener {
    private DataCenter dataCenter = DataCenter.getInstance();
    private Lesson lesson;
    private RatingBar ratingbar;
    private Button flashcardBtn, editBtn, testBtn, addToMyListBtn;
    private TextView lessonTitleTextView, topicTextView, authorTextView, numberOfCardTextView, addToMyListTextView;
    private ListView flashcardListView;
    private SmallFlashCardListAdapter smallFlashCardListAdapter;
    private FirebaseManagement fm = FirebaseManagement.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                lesson = null;
            } else {
                lesson = (Lesson) extras.getSerializable("lesson");
            }
        } else {
            lesson = (Lesson) savedInstanceState.getSerializable("lesson");
        }
//        Log.d("lesson ID", lessonID);
//        lesson = dataCenter.getLessonByID(lessonID);
        setContentView(R.layout.activity_lesson_view);
        initComponent();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initComponent() {
        flashcardBtn = findViewById(R.id.flashCardBtn);
        editBtn = findViewById(R.id.EditBtn);
        testBtn = findViewById(R.id.TestBtn);
        addToMyListBtn = findViewById(R.id.addToMyListBtn);
        ratingbar = findViewById(R.id.rating);
        ratingbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                openDialog();
                return false;
            }
        });
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(LessonViewActivity.this, QuizViewActivity.class);
                myIntent.putExtra("lesson", lesson); //Optional parameters
                LessonViewActivity.this.startActivity(myIntent);
            }
        });
        lessonTitleTextView = findViewById(R.id.LessonName);
        authorTextView = findViewById(R.id.authorName);
        topicTextView = findViewById(R.id.topic);
        numberOfCardTextView = findViewById(R.id.numberOfCard);
        addToMyListTextView = findViewById(R.id.addToMyListTextView);
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
        if (dataCenter.user.containLessonID(lesson.getLessonID())) {
            addToMyListBtn.setEnabled(false);
//            addToMyListBtn.setback();
            addToMyListTextView.setText("Added to your list");
        } else {
            addToMyListBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(LessonViewActivity.this, EditScheduleActivity.class);
                    myIntent.putExtra("lesson", lesson); //Optional parameters
                    LessonViewActivity.this.startActivity(myIntent);
                }
            });
        }
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = LessonViewActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private ListView.OnItemClickListener listViewItemOnClick = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent myIntent = new Intent(LessonViewActivity.this, FlashcardViewActivity.class);
            myIntent.putExtra("lesson", lesson); //Optional parameters
            LessonViewActivity.this.startActivity(myIntent);
        }
    };

    public void openDialog() {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");
        StarRatingDialog starRatingDialog = new StarRatingDialog();
        starRatingDialog.show(getSupportFragmentManager(), "tag");
    }

    @Override
    public void updateRating(float rating) {
        lesson.setRating(Math.min((float) (lesson.getRating() + 0.5 * (lesson.getRating() - rating)), 5));
        fm.addLessonByID(lesson);
        ratingbar.setRating(lesson.getRating());
    }
}