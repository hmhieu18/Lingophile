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

import com.example.lingophile.Models.User;
import com.example.lingophile.R;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private Context _context;
    private int _layoutID;
    private List<User> _items;


    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        _context = context;
        _layoutID = resource;
        _items = objects;
    }

    @Override
    public int getCount() {
        return _items.size();
    }

    public User getItem(int position){
        return _items.get(position);
    }

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(_context);
            convertView = layoutInflater.inflate(_layoutID,null, false);
        }
        TextView name = (TextView)convertView.findViewById(R.id.authorName);
        TextView des = (TextView)convertView.findViewById(R.id.authorDescription);
        RatingBar rating = (RatingBar)convertView.findViewById(R.id.rating);
        User user = _items.get(position);
        if (user != null){
            rating.setRating(user.getRating());
            name.setText(user.getEmail());
            des.setText(user.getUsername());
        }
        return convertView;
    }
}
