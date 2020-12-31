package com.example.lingophile.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Helper.ReadDataListener;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.WeekScheduleItem;
import com.example.lingophile.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DailyScheduleArrayAdapter extends ArrayAdapter<WeekScheduleItem.ScheduleItem> {
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

        final TextView lessonName = convertView.findViewById(R.id.lessonName1);
        final TextView topic = convertView.findViewById(R.id.topic1);
        final TextView authorName = convertView.findViewById(R.id.authorName1);
        final TextView textView = convertView.findViewById(R.id.hourminute);


        final WeekScheduleItem.ScheduleItem scheduleItem = _items.get(position);
        if (scheduleItem != null) {
            firebaseManagement.loadLessonByID(scheduleItem.getlesson(), new ReadDataListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onFinish() {

                }

                @Override
                public void onFail() {

                }

                @Override
                public void updateUI() {

                }

                @Override
                public void onListenLessonSuccess(Lesson lesson) {
                    lessonName.setText("Tile: " + lesson.getTitle());
                    topic.setText("Topic: " + lesson.getDescription());
                    String author = lesson.getAuthorName();
                    if (author.length() > 15) {
                        author = author.substring(0, 15);
                    }
                    authorName.setText("Author: " + author);
                    textView.setText(Integer.valueOf(scheduleItem.getHour()).toString() + ':' + Integer.valueOf(scheduleItem.getMin()).toString());
                }
            });

        }
        return convertView;
    }
}
