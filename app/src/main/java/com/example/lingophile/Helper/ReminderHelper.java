package com.example.lingophile.Helper;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.Schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class ReminderHelper {
    private static String getCalendarUriBase(Activity activity) {

        String calendarUriBase = null;
        Uri calendars = Uri.parse("content://calendar/calendars");
        Cursor managedCursor = null;
        try {
            managedCursor = activity.managedQuery(calendars, null, null, null, null);
        } catch (Exception e) {
        }
        if (managedCursor != null) {
            calendarUriBase = "content://calendar/";
        } else {
            calendars = Uri.parse("content://com.android.calendar/calendars");
            try {
                managedCursor = activity.managedQuery(calendars, null, null, null, null);
            } catch (Exception e) {
            }
            if (managedCursor != null) {
                calendarUriBase = "content://com.android.calendar/";
            }
        }
        return calendarUriBase;
    }

    public static long setReminder(Activity activity, Schedule schedule, String lessonName) {
        if(schedule.dayOfWeek.size()>0)
        {        // get calendar
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, schedule.hour);
            cal.set(Calendar.MINUTE, schedule.min);
            Uri EVENTS_URI = Uri.parse(getCalendarUriBase(activity) + "events");
            ContentResolver cr = activity.getContentResolver();

            // event insert
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.TITLE, "It's time to learn " + lessonName);
            values.put(CalendarContract.Events.ALL_DAY, 0);
            values.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis()); // event starts at 11 minutes from now
            values.put(CalendarContract.Events.DTEND, cal.getTimeInMillis() + 5 * 60 * 1000); // ends 60 minutes from now
            values.put(CalendarContract.Events.RRULE, "FREQ=Weekly;" + getWeekDayAsString(schedule.dayOfWeek));
            values.put(CalendarContract.Events.HAS_ALARM, 1);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
            Uri event = cr.insert(EVENTS_URI, values);

            // reminder insert
            Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(activity) + "reminders");
            values = new ContentValues();
            values.put("event_id", Long.parseLong(event.getLastPathSegment()));
            values.put("method", 1);
            values.put("minutes", 10);
            Log.d("@@@", cr.toString() + "-" + values.toString() + "-" + REMINDERS_URI.toString());
            cr.insert(REMINDERS_URI, values);
            return Long.parseLong(event.getLastPathSegment());
        }
        return 0;
    }

    private static String getWeekDayAsString(ArrayList<Integer> dayOfWeek) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        if (dayOfWeek.contains(1)) {
            stringArrayList.add("SU");
        }
        if (dayOfWeek.contains(2)) {
            stringArrayList.add("MO");
        }
        if (dayOfWeek.contains(3)) {
            stringArrayList.add("TU");
        }
        if (dayOfWeek.contains(4)) {
            stringArrayList.add("WE");
        }
        if (dayOfWeek.contains(5)) {
            stringArrayList.add("TH");
        }
        if (dayOfWeek.contains(6)) {
            stringArrayList.add("FR");
        }
        if (dayOfWeek.contains(7)) {
            stringArrayList.add("SA");
        }
        String string = "BYDAY=";
        for (int i = 0; i < stringArrayList.size() - 1; i++) {
            string += stringArrayList.get(i) + ',';
        }
        if (stringArrayList.size() > 0) {
            string += stringArrayList.get(stringArrayList.size() - 1);
        }
        return string;
    }

    public static void deleteEvent(Activity activity, long eventID) {
        ContentResolver cr = activity.getContentResolver();
        Uri deleteUri = null;
        deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = cr.delete(deleteUri, null, null);
        Log.i("Calendar", "Rows deleted: " + rows);
    }

    private Uri getCalendarURI(boolean eventUri) {
        Uri calendarURI = null;

        if (android.os.Build.VERSION.SDK_INT <= 7) {
            calendarURI = (eventUri) ? Uri.parse("content://calendar/events") : Uri.parse("content://calendar/calendars");
        } else {
            calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/events") : Uri.parse("content://com.android.calendar/calendars");
        }
        return calendarURI;
    }
}
