package com.example.lingophile.Models;

import java.util.concurrent.ScheduledExecutorService;

public class LessonIDSchedule {
    public LessonIDSchedule(String lessonID, Schedule schedule) {
        this.lessonID = lessonID;
        this.schedule = schedule;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    private String lessonID;
    private Schedule schedule=new Schedule();

}
