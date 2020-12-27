package com.example.lingophile.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lingophile.Models.Lesson;
import com.example.lingophile.R;

import java.util.List;

public class MyLessonListAdapter extends ArrayAdapter<Lesson> {
    private Context _context;
    private int _layoutID;
    private List<Lesson> _items;


    public MyLessonListAdapter(@NonNull Context context, int resource, @NonNull List<Lesson> objects) {
        super(context, resource, objects);
        _context = context;
        _layoutID = resource;
        _items = objects;
    }

    @Override
    public int getCount() {
        return _items.size();
    }

    public Lesson getItem(int position) {
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

        TextView lessonName = convertView.findViewById(R.id.lessonName);
        TextView topic = convertView.findViewById(R.id.topic);
        TextView authorName = convertView.findViewById(R.id.authorName);
        RatingBar rating = convertView.findViewById(R.id.rating);
        final Lesson lesson = _items.get(position);
        if (lesson != null) {
            rating.setRating(lesson.getRating());
//            imageView.setImageResource(R.drawable.ic_wateringicon);
            lessonName.setText("Tile: " + lesson.getTitle());
            topic.setText(lesson.getDescription());
            String author = lesson.getAuthorName();
            if (author.length() > 15) {
                author = author.substring(0, 15);
            }
            authorName.setText("Author: " + author);
        }
        return convertView;
    }
}
