package com.example.lingophile.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Helper.ReminderHelper;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.Schedule;
import com.example.lingophile.R;

import java.util.ArrayList;

public class EditScheduleActivity extends AppCompatActivity {
    private ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
    private CheckBox _privateCheckBox;
    private Button finish;
    private Lesson currentLesson;
    private FirebaseManagement firebaseManagement = FirebaseManagement.getInstance();
    private TimePicker timePicker;
    private TextView repeatTextView;
    private DataCenter dataCenter = DataCenter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                currentLesson = null;
            } else {
                currentLesson = (Lesson) extras.getSerializable("lesson");
            }
        } else {
            currentLesson = (Lesson) savedInstanceState.getSerializable("lesson");
        }
        setContentView(R.layout.fragment_edit_schedule);
        initComponent();
    }

    private void initComponent() {
        _privateCheckBox = findViewById(R.id._privateCheckBox);
        CheckBox temp;
        temp = findViewById(R.id.sun);
        checkBoxArrayList.add(temp);
        temp = findViewById(R.id.mon);
        checkBoxArrayList.add(temp);
        temp = findViewById(R.id.tue);
        checkBoxArrayList.add(temp);
        temp = findViewById(R.id.wed);
        checkBoxArrayList.add(temp);
        temp = findViewById(R.id.thu);
        checkBoxArrayList.add(temp);
        temp = findViewById(R.id.fri);
        checkBoxArrayList.add(temp);
        temp = findViewById(R.id.sat);
        checkBoxArrayList.add(temp);
        setClickListenerForButtonsArrayList();
        timePicker = findViewById(R.id.edit_alarm_time_picker);
        loadOldSchedule();
        repeatTextView = findViewById(R.id.repeat);
        finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finishClicked();
            }
        });
    }

    private void setClickListenerForButtonsArrayList() {

        for (CheckBox cb : checkBoxArrayList) {
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }

    private void loadOldSchedule() {
//        timePicker.setHour(currentLesson.getLearningSchedule().hour);
//        timePicker.setMinute(currentLesson.getLearningSchedule().min);
//        if (currentLesson.learningSchedule.dayOfWeek.size() > 0) {
//            for (int i = 0; i < checkBoxArrayList.size(); i++) {
//                if (currentLesson.getLearningSchedule().dayOfWeek != null)
//                    if (currentLesson.getLearningSchedule().dayOfWeek.contains(i + 1)) {
//                        checkBoxArrayList.get(i).setChecked(true);
//                        countBoxChecked++;
//                    }
//            }
//        }
//        for (CheckBox cb1 : checkBoxArrayList) {
//            if (!cb1.isChecked()) cb1.setEnabled(false);
//        }
    }


    private void finishClicked() {
        int hourFromPicker = timePicker.getHour();
        int minuteFromPicker = timePicker.getMinute();
        if (_privateCheckBox.isChecked()) {
            currentLesson.set_private(true);
        }
        Toast.makeText(this, "Setting Notification...", Toast.LENGTH_SHORT).show();
        ArrayList<Integer> alarmDays = getDayArrayList();
        if (dataCenter.user.getScheduleByLessonID(currentLesson.getLessonID()) != null) {
            ReminderHelper.deleteEvent(this,
                    dataCenter.user.getScheduleByLessonID(currentLesson.getLessonID()).eventID);
        }
        Schedule schedule = new Schedule(alarmDays, hourFromPicker, minuteFromPicker);
        schedule.eventID = ReminderHelper.setReminder(this, schedule, currentLesson.getTitle());
        dataCenter.user.setScheduleByLessonID(currentLesson.getLessonID(), schedule);
        firebaseManagement.addLessonByID(currentLesson);
        firebaseManagement.updateUserLessonList();
        Intent myIntent = new Intent(EditScheduleActivity.this, MainActivity.class);
        EditScheduleActivity.this.startActivity(myIntent);
    }


    private ArrayList<Integer> getDayArrayList() {
        ArrayList<Integer> alarmDays = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (checkBoxArrayList.get(i).isChecked()) alarmDays.add(i + 1);
        }
        return alarmDays;
    }
}