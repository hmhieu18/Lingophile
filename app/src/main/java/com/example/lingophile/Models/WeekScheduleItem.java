package com.example.lingophile.Models;

import java.util.ArrayList;
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
        convertPlantListToSchedule();
    }

    public void convertPlantListToSchedule() {
        for (int i = 1; i < 8; i++) {
            for (LessonIDSchedule p : AppData.user.lessonIDArrayList) {
                if (p.schedule.dayOfWeek != null)
                    for (int day : p.schedule.dayOfWeek) {
                        if (i == day) {
                            dayScheduleItemsArrayList.get(i)
                                    .scheduleItemArrayList.add(new ScheduleItem(p.lessonID, p.getId(p.getLessonID().replaceAll(" ", "").toLowerCase(), R.drawable.class),
                                    p.schedule.hour, p.schedule.min));
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
        private int imageID, hour, min;

        public ScheduleItem(String name, int imageID, int hour, int min) {
            this.lessonID = lessonID;
            this.imageID = imageID;
            this.hour = hour;
            this.min = min;
        }

        public String getLessonID() {
            return lessonID;
        }

        public void setLessonID(String name) {
            this.lessonID = name;
        }

        public int getImageID() {
            return imageID;
        }

        public void setImageID(int imageID) {
            this.imageID = imageID;
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
