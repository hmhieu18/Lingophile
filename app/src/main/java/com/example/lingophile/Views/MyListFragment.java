package com.example.lingophile.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lingophile.Adapter.MyLessonListAdapter;
import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Helper.ReadDataListener;
import com.example.lingophile.Helper.ReminderHelper;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.R;

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
    private ListView.OnItemLongClickListener listViewItemOnLongClick = new ListView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            showOptionDialog(getContext(), i);
            return true;
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
        myListListView.setOnItemLongClickListener(listViewItemOnLongClick);

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

    private void showOptionDialog(final Context context, final int position) {
        final CharSequence[] options = {"Remove this lesson", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Option");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Remove this lesson")) {
                    getWarningDialog(position).show();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void removeLessonByID(int position) {
        ReminderHelper.deleteEvent(Objects.requireNonNull(getActivity()), dataCenter.user.getLessonIDArrayList().get(position).getSchedule().eventID);
        dataCenter.user.getLessonIDArrayList().remove(position);
        firebaseManagement.updateUserLessonList();
        dataCenter.getThisUserLessonArrayList().remove(position);
        refreshListView();
    }

    private AlertDialog.Builder getWarningDialog(final int position) {
        return new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                .setTitle("Remove lesson")
                .setMessage("Are you sure to remove this lesson?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        removeLessonByID(position);
                    }
                })
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert);
    }
}