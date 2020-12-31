package com.example.lingophile.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Helper.ReminderHelper;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.Schedule;
import com.example.lingophile.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditScheduleFragment extends Fragment {
    private ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
    private CheckBox _privateCheckBox;
    private Button finish;
    private Lesson currentLesson;
    private int timetolearn;
    private FirebaseManagement firebaseManagement = FirebaseManagement.getInstance();
    private TimePicker timePicker;
    private TextView repeatTextView;
    private int countBoxChecked = 0;
    private DataCenter dataCenter = DataCenter.getInstance();

    public EditScheduleFragment() {
        // Required empty public constructor
    }

    public static EditScheduleFragment newInstance(Lesson lesson) {
        EditScheduleFragment fragment = new EditScheduleFragment();
        Bundle args = new Bundle();
        args.putSerializable("lesson", lesson);
//        args.putString("plantName", plantName);
//        args.putInt("index", _index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentLesson = (Lesson) getArguments().getSerializable("lesson");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_schedule, container, false);
        initComponent(view);
        return view;
    }

    private void initComponent(View view) {
        _privateCheckBox=view.findViewById(R.id._privateCheckBox);
        CheckBox temp;
        temp = view.findViewById(R.id.sun);
        checkBoxArrayList.add(temp);
        temp = view.findViewById(R.id.mon);
        checkBoxArrayList.add(temp);
        temp = view.findViewById(R.id.tue);
        checkBoxArrayList.add(temp);
        temp = view.findViewById(R.id.wed);
        checkBoxArrayList.add(temp);
        temp = view.findViewById(R.id.thu);
        checkBoxArrayList.add(temp);
        temp = view.findViewById(R.id.fri);
        checkBoxArrayList.add(temp);
        temp = view.findViewById(R.id.sat);
        checkBoxArrayList.add(temp);
        setClickListenerForButtonsArrayList();
        timePicker = view.findViewById(R.id.edit_alarm_time_picker);
        loadOldSchedule();
        repeatTextView = view.findViewById(R.id.repeat);
        finish = view.findViewById(R.id.finish);
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
        if(_privateCheckBox.isChecked())
        {
            currentLesson.set_private(true);
        }
        Toast.makeText(getContext(), "Setting Notification...", Toast.LENGTH_SHORT).show();
        ArrayList<Integer> alarmDays = getDayArrayList();
        if (dataCenter.user.getScheduleByLessonID(currentLesson.getLessonID())!=null) {
            ReminderHelper.deleteEvent(getActivity(),
                    dataCenter.user.getScheduleByLessonID(currentLesson.getLessonID()).eventID);
        }
        Schedule schedule = new Schedule(alarmDays, hourFromPicker, minuteFromPicker);
        schedule.eventID=ReminderHelper.setReminder(getActivity(), schedule, currentLesson.getTitle());
        dataCenter.user.setScheduleByLessonID(currentLesson.getLessonID(), schedule);
        firebaseManagement.addLessonByID(currentLesson);
        firebaseManagement.updateUserLessonList();
        openFragment(MyListFragment.newInstance("", ""));
    }


    private ArrayList<Integer> getDayArrayList() {
        ArrayList<Integer> alarmDays = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (checkBoxArrayList.get(i).isChecked()) alarmDays.add(i + 1);
        }
        return alarmDays;
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
