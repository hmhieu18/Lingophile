package com.example.lingophile.Models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import com.example.lingophile.Models.WeekScheduleItem;
import com.example.lingophile.R;

public class DailyScheduleArrayAdapter extends ArrayAdapter<WeekScheduleItem.ScheduleItem> {
    private Context _context;
    private int _layoutID;
    private List<WeekScheduleItem.ScheduleItem> _items;


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

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(_context);
            convertView = layoutInflater.inflate(_layoutID, null, false);
        }

        ImageView imageView = convertView.findViewById(R.id.icon);
        TextView textView = convertView.findViewById(R.id.lessonname);
        TextView textViewSub = convertView.findViewById(R.id.description);
        TextView textViewRating = convertView.findViewById(R.id.percentage);
        final WeekScheduleItem.ScheduleItem scheduleItem = _items.get(position);
        if (scheduleItem != null) {
            imageView.setImageResource(R.drawable.ic_calendar);
            textView.setText(Integer.valueOf(scheduleItem.getHour()).toString() + ':' + Integer.valueOf(scheduleItem.getMin()).toString());
            textViewSub.setText("Learning schedule " + scheduleItem.getLessonID());
            textViewRating.setText("");
        }
        return convertView;
    }
}