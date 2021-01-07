package com.example.lingophile.Adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.example.lingophile.Models.QuizCard;
import com.example.lingophile.R;

import java.util.ArrayList;
import java.util.List;

public class QuizCardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<QuizCard> mData;
    private float mBaseElevation;
    private int numberOfCorrect = 0;
    private Activity activity;

    public QuizCardPagerAdapter(Activity activity) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.activity = activity;
    }

    public void addCardItem(QuizCard item) {
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

    public QuizCard getFlashcardAt(int position) {
        return mData.get(position);
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
                .inflate(R.layout.quiz_item, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
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

    @SuppressLint("SetTextI18n")
    private void bind(final QuizCard item, View view) {
        TextView noQuestionTextView = (TextView) view.findViewById(R.id.noQuestion);
        TextView questionTextView = (TextView) view.findViewById(R.id.question);
        final Button optionA = view.findViewById(R.id.optionA);
        final Button optionB = view.findViewById(R.id.optionB);
        final Button optionC = view.findViewById(R.id.optionC);
//        noQuestionTextView.setText(item.getFlashCard().getWord());
        questionTextView.setText(item.getFlashCard().getWord() + " means");
        optionA.setText(item.getOptions().get(0));
        optionB.setText(item.getOptions().get(1));
        optionC.setText(item.getOptions().get(2));
        optionA.setOnClickListener(getListener(item, optionA, optionB, optionC));
        optionB.setOnClickListener(getListener(item, optionA, optionB, optionC));
        optionB.setOnClickListener(getListener(item, optionA, optionB, optionC));
    }

    private View.OnClickListener getListener(final QuizCard item, final Button optionA, final Button optionB, final Button optionC) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button a = (Button) view;
                if (a.getText() == item.getFlashCard().getMeaning()) {
                    Drawable buttonDrawable = a.getBackground();
                    buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                    DrawableCompat.setTint(buttonDrawable, Color.GREEN);
                    a.setBackground(buttonDrawable);
                    numberOfCorrect++;
                } else {
                    Drawable buttonDrawable = a.getBackground();
                    buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                    DrawableCompat.setTint(buttonDrawable, Color.RED);
                    a.setBackground(buttonDrawable);
                    if (optionA.getText() == item.getFlashCard().getMeaning())
                        buttonDrawable = optionA.getBackground();

                    if (optionB.getText() == item.getFlashCard().getMeaning())
                        buttonDrawable = optionB.getBackground();

                    if (optionC.getText() == item.getFlashCard().getMeaning())
                        buttonDrawable = optionC.getBackground();

                    buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                    DrawableCompat.setTint(buttonDrawable, Color.GREEN);
                    a.setBackground(buttonDrawable);
                }
            }
        };
    }

}
