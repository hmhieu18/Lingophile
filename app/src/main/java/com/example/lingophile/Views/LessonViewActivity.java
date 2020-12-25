package com.example.lingophile.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.example.lingophile.Adapter.SmallFlashCardListAdapter;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.R;

public class LessonViewActivity extends AppCompatActivity {

    private Lesson lesson;
    Button flashcardBtn, editBtn, testBtn;
    private ListView flashcardListView;
    private SmallFlashCardListAdapter smallFlashCardListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_view);
        initComponent();
    }

    private void initComponent() {
        lessonToUI();
        flashcardBtn = findViewById(R.id.flashCardBtn);
        editBtn = findViewById(R.id.EditBtn);
        testBtn = findViewById(R.id.TestBtn);
        flashcardListView = findViewById(R.id.flashcardListView);
        smallFlashCardListAdapter = new SmallFlashCardListAdapter(this, R.layout.small_flashcard_item, lesson.getFlashCardArrayList());
        flashcardListView.setAdapter(smallFlashCardListAdapter);
        flashcardListView.setOnItemClickListener(listViewItemOnClick);
    }

    private void lessonToUI() {

    }

    private ListView.OnItemClickListener listViewItemOnClick = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            openFragment(PlantDetailsFragment.newInstance(AppData.user.userPlants.get(position), position, false));
        }
    };
}