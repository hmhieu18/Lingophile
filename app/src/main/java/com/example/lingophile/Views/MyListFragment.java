package com.example.lingophile.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lingophile.Adapter.MyLessonListAdapter;
import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Helper.ReadDataListener;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.LessonIDSchedule;
import com.example.lingophile.Models.Schedule;
import com.example.lingophile.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MyListFragment extends Fragment {
    private FirebaseManagement firebaseManagement = FirebaseManagement.getInstance();
    private ListView myListListView;
    private MyLessonListAdapter myLessonListAdapter;
    private DataCenter dataCenter = DataCenter.getInstance();


    public MyListFragment() {
        // Required empty public constructor
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static MyListFragment newInstance(String param1, String param2) {
        MyListFragment fragment = new MyListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_list, container, false);
        initComponent(view);
        return view;
    }

    private ListView.OnItemClickListener itemClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent myIntent = new Intent(getActivity(), LessonViewActivity.class);
            myIntent.putExtra("lesson", dataCenter.getThisUserLessonArrayList().get(position)); //Optional parameters
            Objects.requireNonNull(getActivity()).startActivity(myIntent);
        }
    };

    private void initComponent(View view) {
        myListListView = view.findViewById(R.id.lessonListview);
        firebaseManagement.loadUserLessonIDList(new ReadDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFail() {

            }

            @Override
            public void updateUI() {
                refreshListView();
            }

            @Override
            public void onListenLessonSuccess(Lesson lesson) {

            }
        });
        myListListView.setOnItemClickListener(itemClickListener);
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void refreshListView() {
        myLessonListAdapter = new MyLessonListAdapter(getContext(), R.layout.lesson_item, dataCenter.getThisUserLessonArrayList());
        myListListView.setAdapter(myLessonListAdapter);
    }
}