package com.example.lingophile.Views;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.lingophile.Adapter.QuizCardPagerAdapter;
import com.example.lingophile.Adapter.ShadowTransformer;
import com.example.lingophile.Models.FlashCard;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.QuizCard;
import com.example.lingophile.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//import com.example.lingophile.Adapter.CardFragmentPagerAdapter;

public class QuizViewActivity extends AppCompatActivity {

    final public static int RESULT_SPEECH = 991;
    final View.OnClickListener hearClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

            try {
                QuizViewActivity.this.startActivityForResult(intent, RESULT_SPEECH);
            } catch (ActivityNotFoundException ignored) {
            }
        }
    };
    private Lesson lesson;
    private TextView titleTextView;
    private ArrayList<QuizCard> quizCardArrayList = new ArrayList<>();
    private ViewPager mViewPager;
    private QuizCardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private Button nextQuizButton;

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
        setContentView(R.layout.activity_quizcard_view);
        initComponent();
    }

    private void lessonToQuizList() {
        for (FlashCard f : lesson.getFlashCardArrayList()) {
            Random rand = new Random();
            String str1 = f.getMeaning();
            String str2 = f.getMeaning();
            while (str1.equals(f.getMeaning()))
                str1 = lesson.getFlashCardArrayList().get(rand.nextInt(lesson.getFlashCardArrayList().size())).getMeaning();

            while (str2.equals(f.getMeaning()) || str2.equals(str1))
                str2 = lesson.getFlashCardArrayList().get(rand.nextInt(lesson.getFlashCardArrayList().size())).getMeaning();
            ArrayList<String> options = new ArrayList<>();
            options.add(str1);
            options.add(str2);
            options.add(f.getMeaning());
            Collections.shuffle(options);
            quizCardArrayList.add(new QuizCard(f, options));
        }
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    @SuppressLint("SetTextI18n")
    private void initComponent() {
        titleTextView = findViewById(R.id.lessonTitle);
        titleTextView.setText(capitalize(lesson.getTitle()) + " Quiz");
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.beginFakeDrag();
        mCardAdapter = new QuizCardPagerAdapter(this);
        lessonToQuizList();

        for (QuizCard quizCard : quizCardArrayList) {
            mCardAdapter.addCardItem(quizCard);
        }

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        nextQuizButton = findViewById(R.id.nextQuizButton);
        nextQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mViewPager.getCurrentItem() < mCardAdapter.getCount())
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                else {
                    quizFinish();
                }
            }
        });
    }

    private void quizFinish() {

    }
}
