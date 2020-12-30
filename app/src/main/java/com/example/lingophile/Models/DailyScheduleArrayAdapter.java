package com.example.lingophile.Models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Helper.ReadDataListener;
import com.example.lingophile.Models.WeekScheduleItem;
import com.example.lingophile.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

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