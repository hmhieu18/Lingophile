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

import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Helper.ReadDataListener;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.User;
import com.example.lingophile.Models.WeekScheduleItem;
import com.example.lingophile.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

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

    public static class DailyScheduleArrayAdapter extends ArrayAdapter<WeekScheduleItem.ScheduleItem> {
        private Context _context;
        private int _layoutID;
        private List<WeekScheduleItem.ScheduleItem> _items;
        private FirebaseManagement firebaseManagement = FirebaseManagement.getInstance();

        public DailyScheduleArrayAdapter(@NonNull Context context, int resource, @NonNull List<WeekScheduleItem.ScheduleItem> objects) {
            super(context, resource, objects);
            _context = context;
            _layoutID = resource;
            _items = objects;
        }

        @Override
        public int getCount() {
            return _items.size();
        }

        public WeekScheduleItem.ScheduleItem getItem(int position) {
            return _items.get(position);
        }

        private void loadLessonOfUser(String lessonID, final ReadDataListener readdata) {
            DatabaseReference ref = firebaseManagement.getDatabaseReference().child("lessons_list").child(lessonID);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Lesson temp = dataSnapshot.getValue(Lesson.class);
                    readdata.onFinish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }



        @SuppressLint({"SetTextI18n", "ResourceType"})
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(_context);
                convertView = layoutInflater.inflate(_layoutID, null, false);
            }

            TextView lessonName = convertView.findViewById(R.id.lessonName1);
            TextView topic = convertView.findViewById(R.id.topic1);
            TextView authorName = convertView.findViewById(R.id.authorName1);
            TextView textView = convertView.findViewById(R.id.hourminute);


            final WeekScheduleItem.ScheduleItem scheduleItem = _items.get(position);
            if (scheduleItem != null) {
                //Lesson tmp=loadLessonOfUser(scheduleItem.getlesson());

                lessonName.setText("Tile: ");
                topic.setText("Topic: ");
                //String author = tmp.getAuthorName();
                //if (author.length() > 15) {
                //    author = author.substring(0, 15);
                //}
                authorName.setText("Author: ");
                textView.setText(Integer.valueOf(scheduleItem.getHour()).toString() + ':' + Integer.valueOf(scheduleItem.getMin()).toString());
            }
            return convertView;
        }
    }
}
