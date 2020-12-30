package com.example.lingophile.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.lingophile.Models.FlashCard;
import com.example.lingophile.R;
import com.example.lingophile.Views.LoginActivity;
import com.example.lingophile.Views.RegisterFormActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<FlashCard> mData;
    private List<Button> mButton;
    private float mBaseElevation;
    private TextToSpeech mText2Speech;
    private boolean mIsText2SpeechReady = false;

    public CardPagerAdapter(Activity activity) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        mText2Speech = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                mIsText2SpeechReady = true;
            }
        });
        mText2Speech.setLanguage(Locale.ENGLISH);
    }

    public void addCardItem(FlashCard item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(final FlashCard item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        Button speak = view.findViewById(R.id.speak);
        Button hear = view.findViewById(R.id.hear);
        speak.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mIsText2SpeechReady) {
                    mText2Speech.speak(item.getWord(),
                            TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
//        hear.setOnClickListener(hearClick);
        titleTextView.setText(item.getWord());
        contentTextView.setText(item.getMeaning());
    }

    //
//
//    View.OnClickListener hearClick = new View.OnClickListener() {
//        public void onClick(View v) {
////            Intent myIntent = new Intent(LoginFormActivity.this, RegisterFormActivityFormActivity.class);
////            LoginActivity.this.startActivity(myIntent);
//        }
//    };
}
