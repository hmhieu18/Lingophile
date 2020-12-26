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
            for (Lesson p : AppData.user.userLessons) {
                if (p.learningSchedule.dayOfWeek != null)
                    for (int day : p.learningSchedule.dayOfWeek) {
                        if (i == day) {
                            dayScheduleItemsArrayList.get(i)
                                    .scheduleItemArrayList.add(new ScheduleItem(p.name, p.getId(p.getName().replaceAll(" ", "").toLowerCase(), R.drawable.class),
                                    p.learningSchedule.hour, p.learningSchedule.min));
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
        private String name;
        private int imageID, hour, min;

        public ScheduleItem(String name, int imageID, int hour, int min) {
            this.name = name;
            this.imageID = imageID;
            this.hour = hour;
            this.min = min;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
