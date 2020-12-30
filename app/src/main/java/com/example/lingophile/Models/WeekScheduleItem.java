package com.example.lingophile.Models;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.R;

public class WeekScheduleItem {
    public ArrayList<DayScheduleItem> getDayScheduleItemsArrayList() {
        return dayScheduleItemsArrayList;
    }

    public void setDayScheduleItemsArrayList(ArrayList<DayScheduleItem> dayScheduleItemsArrayList) {
        this.dayScheduleItemsArrayList = dayScheduleItemsArrayList;
    }

    private ArrayList<DayScheduleItem> dayScheduleItemsArrayList = new ArrayList<>();


    public WeekScheduleItem() {
        for (int i = 0; i < 8; i++) {
            dayScheduleItemsArrayList.add(new DayScheduleItem());
        }
        convertLessonListToSchedule();
    }

    public void convertLessonListToSchedule() {
        for (int i = 1; i < 8; i++) {
            for (LessonIDSchedule p : DataCenter.getInstance().user.lessonIDArrayList) {
                if (p.schedule.dayOfWeek != null)
                    for (int day : p.schedule.dayOfWeek) {
                        if (i == day) {
                            dayScheduleItemsArrayList.get(i)
                                    .scheduleItemArrayList.add(new ScheduleItem(p.lessonID, p.schedule.hour, p.schedule.min));
                        }
                    }
            }
        }

    }

    public class DayScheduleItem {
        public ArrayList<ScheduleItem> getScheduleItemArrayList() {
            return scheduleItemArrayList;
        }

        public void setScheduleItemArrayList(ArrayList<ScheduleItem> scheduleItemArrayList) {
            this.scheduleItemArrayList = scheduleItemArrayList;
        }

        public DayScheduleItem() {
            scheduleItemArrayList = new ArrayList<>();
        }

        private ArrayList<ScheduleItem> scheduleItemArrayList;
    }


    public class ScheduleItem {
        private String lessonID;
        private int hour, min;

        public ScheduleItem(String lesson, int hour, int min) {
            this.lessonID = lesson;
            this.hour = hour;
            this.min = min;
        }

        public String getlesson() {
            return lessonID;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }


}
