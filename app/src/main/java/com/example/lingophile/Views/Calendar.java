package com.example.lingophile.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Models.DailyScheduleArrayAdapter;
import com.example.lingophile.Models.WeekScheduleItem;
import com.example.lingophile.R;
import java.util.ArrayList;
import java.util.Objects;

public class Calendar extends Fragment {
    private FirebaseManagement firebaseManagement = FirebaseManagement.getInstance();
    private ArrayList<Button> buttonArrayList = new ArrayList<>();
    private TextView dayTextView;
    private WeekScheduleItem weekScheduleItem = new WeekScheduleItem();
    private ListView scheduleListView;
    public static DailyScheduleArrayAdapter scheduleArrayAdapter;

    public Calendar() {
        // Required empty public constructor
    }
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static Calendar newInstance(String param1, String param2) {
        Calendar fragment = new Calendar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        initComponent(view);
        return view;
    }

    private void initComponent(View view) {
        scheduleListView = view.findViewById(R.id.scheduleListView);
        scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent myIntent = new Intent(PredictionFragment.this, searchResult.class);
//                myIntent.putExtra("query", String.valueOf(suggestionsArrayList.get(position).getName()));
//                PredictionFragment.this.startActivity(myIntent);
            }
        });

        dayTextView = view.findViewById(R.id.dayOfWeekTextView);
        Button temp = view.findViewById(R.id.sunBtn);
        buttonArrayList.add(temp);
        temp = view.findViewById(R.id.monBtn);
        buttonArrayList.add(temp);
        temp = view.findViewById(R.id.tueBtn);
        buttonArrayList.add(temp);
        temp = view.findViewById(R.id.wedBtn);
        buttonArrayList.add(temp);
        temp = view.findViewById(R.id.thuBtn);
        buttonArrayList.add(temp);
        temp = view.findViewById(R.id.friBtn);
        buttonArrayList.add(temp);
        temp = view.findViewById(R.id.satBtn);
        buttonArrayList.add(temp);
        for (Button b : buttonArrayList)
            b.setOnClickListener(dayOfWeekListener);
    }

    private void setAdapterListView(int dayInt) {
        scheduleArrayAdapter = new DailyScheduleArrayAdapter(getContext(), R.layout.prediction_item,
                weekScheduleItem.getDayScheduleItemsArrayList().get(dayInt).getScheduleItemArrayList());
        scheduleListView.setAdapter(scheduleArrayAdapter);
    }

    private View.OnClickListener dayOfWeekListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.monBtn:
                    dayTextView.setText("Monday");
                    setAdapterListView(2);
                    break;
                case R.id.tueBtn:
                    dayTextView.setText("Tuesday");
                    setAdapterListView(3);

                    break;
                case R.id.wedBtn:
                    dayTextView.setText("Wednesday");
                    setAdapterListView(4);
                    break;
                case R.id.thuBtn:
                    dayTextView.setText("Thursday");
                    setAdapterListView(5);
                    break;
                case R.id.friBtn:
                    dayTextView.setText("Friday");
                    setAdapterListView(6);
                    break;
                case R.id.satBtn:
                    dayTextView.setText("Saturday");
                    setAdapterListView(7);
                    break;
                case R.id.sunBtn:
                    dayTextView.setText("Sunday");
                    setAdapterListView(1);
                    break;
            }
        }
    };
}