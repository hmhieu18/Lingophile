package com.example.lingophile.Views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lingophile.Models.Lesson;
import com.example.lingophile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements FlashcardInputDialog.ExampleDialogListener {
    private TextView fragmentName;
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_my_list:
                            fragmentName.setText("My List");
                            openFragment(MyListFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_calendar:
                            fragmentName.setText("Schedule Management");
                            openFragment(Calendar.newInstance());
                            return true;
                        case R.id.navigation_new_lesson:
                            fragmentName.setText("New Lesson");
                            openFragment(NewLessonFragment.newInstance(null));
                            return true;
                        case R.id.navigation_search:
                            fragmentName.setText("Search");
                            openFragment(SearchFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_setting:
                            fragmentName.setText("Settings");
                            openFragment(SettingsFragment.newInstance("", ""));
                            return true;
                        default:
                            return false;
                    }
                }
            };
    BottomNavigationView bottomNavigation;
    private Lesson lesson;

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @SuppressLint("SetTextI18n")
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
        setContentView(R.layout.activity_main);
        fragmentName = findViewById(R.id.fragmentName);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        if (lesson == null) {
            bottomNavigation.setSelectedItemId(R.id.navigation_my_list);
            fragmentName.setText("My List");
            openFragment(MyListFragment.newInstance("", ""));
        } else {
            bottomNavigation.setSelectedItemId(R.id.navigation_new_lesson);
            fragmentName.setText("New Lesson");
            openFragment(NewLessonFragment.newInstance(lesson));
        }
    }

    @Override
    public void applyTexts(int position, String word, String meaning) {
        NewLessonFragment.flashCardArrayList.get(position).setWord(word);
        NewLessonFragment.flashCardArrayList.get(position).setMeaning(meaning);
        NewLessonFragment.smallFlashCardListAdapter.notifyDataSetChanged();
    }
}