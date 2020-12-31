package com.example.lingophile.ViewModels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.lingophile.Adapter.UserAdapter;
import com.example.lingophile.Models.WeekScheduleItem;
import com.example.lingophile.R;
import java.util.ArrayList;

public class Calendar extends Fragment {
    private ArrayList<Button> buttonArrayList = new ArrayList<>();
    private TextView dayTextView;
    private WeekScheduleItem weekScheduleItem = new WeekScheduleItem();
    private ListView scheduleListView;
    public static UserAdapter.DailyScheduleArrayAdapter scheduleArrayAdapter;

    public Calendar() {
        // Required empty public constructor
    }

    public static Calendar newInstance() {
        return new Calendar();
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
        scheduleArrayAdapter = new UserAdapter.DailyScheduleArrayAdapter(getContext(), R.layout.prediction_item,
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