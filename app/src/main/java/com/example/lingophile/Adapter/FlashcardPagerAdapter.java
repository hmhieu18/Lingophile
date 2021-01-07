package com.example.lingophile.Adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.lingophile.Models.FlashCard;
import com.example.lingophile.R;

import java.util.ArrayList;
import java.util.List;

public class FlashcardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<FlashCard> mData;
    private float mBaseElevation;

    private Activity activity;

    public FlashcardPagerAdapter(Activity activity) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.activity = activity;
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
    public FlashCard getFlashcardAt(int position) {
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
                .inflate(R.layout.flashcard_item, container, false);
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

    private void bind(final FlashCard item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        titleTextView.setText(item.getWord());
        contentTextView.setText(item.getMeaning());
    }

}
