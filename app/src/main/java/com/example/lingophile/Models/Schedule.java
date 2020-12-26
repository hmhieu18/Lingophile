package com.example.lingophile.Models;

import java.util.ArrayList;

public class Schedule {
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
        this.dayOfWeek = new ArrayList<>();
        this.hour = 0;
        this.min = 0;
    }
}
