package com.example.lingophile.Views;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.lingophile.Adapter.FlashcardPagerAdapter;
import com.example.lingophile.Adapter.ShadowTransformer;
import com.example.lingophile.Models.FlashCard;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.R;

import java.util.ArrayList;
import java.util.Locale;

//import com.example.lingophile.Adapter.CardFragmentPagerAdapter;

public class FlashcardViewActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private Lesson lesson;
    private ViewPager mViewPager;
    private Button speak;
    private Button hear;
    private TextView listenText;
    private FlashcardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    //    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private TextToSpeech mText2Speech;
    private boolean mIsText2SpeechReady = false;

    private boolean mShowingFragments = false;

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
        setContentView(R.layout.activity_flashcard_view);
        initComponent();
    }

    private void initComponent() {
        listenText = findViewById(R.id.textListen);
        mText2Speech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                mIsText2SpeechReady = true;
            }
        });
        mText2Speech.setLanguage(Locale.ENGLISH);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mCardAdapter = new FlashcardPagerAdapter(this);
        for (FlashCard flashCard : lesson.getFlashCardArrayList()) {
            mCardAdapter.addCardItem(flashCard);
        }
//        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
//                dpToPixels(2, this));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
//        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        hear = findViewById(R.id.speaker);
        speak = findViewById(R.id.micro);

        hear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FlashCard currentCard = mCardAdapter.getFlashcardAt(mViewPager.getCurrentItem());
                if (mIsText2SpeechReady) {
                    mText2Speech.setLanguage(Locale.ENGLISH);
                    mText2Speech.speak(currentCard.getWord(),
                            TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        speak.setOnClickListener(hearClick);
    }

    @Override
    public void onClick(View view) {
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCardShadowTransformer.enableScaling(b);
        mFragmentCardShadowTransformer.enableScaling(b);
    }

    final public static int RESULT_SPEECH = 991;
    final View.OnClickListener hearClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

            try {
                FlashcardViewActivity.this.startActivityForResult(intent, RESULT_SPEECH);
            } catch (ActivityNotFoundException ignored) {
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    listenText.setText(text.get(0));
                    FlashCard currentCard = mCardAdapter.getFlashcardAt(mViewPager.getCurrentItem());
                    if (text.get(0).toLowerCase().equals(currentCard.getWord().toLowerCase())) {
                        listenText.setTextColor(Color.GREEN);
                    } else
                        listenText.setTextColor(Color.RED);

                }
                break;
            }
        }
    }
}
