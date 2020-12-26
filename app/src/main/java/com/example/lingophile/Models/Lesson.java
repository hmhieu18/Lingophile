package com.example.lingophile.Models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

public class Lesson {
    public String name;
    public Schedule learningSchedule;

    public Lesson(String _name) {
        this.name = _name;
    }

    public Lesson() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }

    public static class Schedule {
        public ArrayList<Integer> dayOfWeek;
        public int hour, min;

        public Schedule(ArrayList<Integer> dayOfWeek, int hour, int min, long eventID) {
            this.dayOfWeek = dayOfWeek;
            this.hour = hour;
            this.min = min;
            this.eventID = eventID;
        }

        public long eventID;

        public Schedule(ArrayList<Integer> dayOfWeek, int hour, int min) {
            this.dayOfWeek = dayOfWeek;
            this.hour = hour;
            this.min = min;
        }

        public Schedule() {
        }
    }
}