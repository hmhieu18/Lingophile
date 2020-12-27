package com.example.lingophile.Models;

import java.lang.reflect.Field;
import java.util.concurrent.ScheduledExecutorService;

public class LessonIDSchedule {
    public LessonIDSchedule(String lessonID, Schedule schedule) {
        this.lessonID = lessonID;
        this.schedule = schedule;
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

    public String lessonID;
    public Schedule schedule=new Schedule();

}
