package com.example.lingophile.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lingophile.Models.FlashCard;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.R;

import java.util.List;

public class SmallFlashCardListAdapter extends ArrayAdapter<FlashCard> {
    private Context _context;
    private int _layoutID;
    private List<FlashCard> _items;


    public SmallFlashCardListAdapter(@NonNull Context context, int resource, @NonNull List<FlashCard> objects) {
        super(context, resource, objects);
        _context = context;
        _layoutID = resource;
        _items = objects;
    }

    @Override
    public int getCount() {
        return _items.size();
    }

    public FlashCard getItem(int position) {
        return _items.get(position);
    }

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(_context);
            convertView = layoutInflater.inflate(_layoutID, null, false);
        }

        TextView meaning = convertView.findViewById(R.id.meaning);
        TextView word = convertView.findViewById(R.id.word);
        TextView index = convertView.findViewById(R.id.index);
        final FlashCard flashCard = _items.get(position);
        if (flashCard != null) {
            word.setText(flashCard.getWord());
            meaning.setText(flashCard.getMeaning());
            index.setText(Integer.toString(position + 1));
        }

        return convertView;
    }
}
