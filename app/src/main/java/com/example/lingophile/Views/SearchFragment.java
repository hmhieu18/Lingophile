package com.example.lingophile.Views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lingophile.Adapter.MyLessonListAdapter;
import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.User;
import com.example.lingophile.R;

import java.util.ArrayList;
import java.util.Objects;

public class SearchFragment extends Fragment {
    private EditText query;
    private Button search;
    private static ArrayList<User> arrayListUser;
    private static ArrayList<Lesson> arrayListLesson;
    private ListView listView;
    private FirebaseManagement fm = FirebaseManagement.getInstance();
    private MyLessonListAdapter mLessonListAdapter;

    public SearchFragment(){

    }
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static SearchFragment newInstance(String param1, String param2){
        SearchFragment searchFragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        searchFragment.setArguments(args);
        return searchFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        query = view.findViewById(R.id.text_search);
        search = view.findViewById(R.id.search_button_view);
        listView = view.findViewById(R.id.list_search);
        search.setOnClickListener(searchListener);

    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String text = query.getText().toString();
            Log.d("@@@", "OKKKK");
            if (text != null){
                fm.requestLessonSearch(text);
                arrayListLesson = DataCenter.getInstance().getLessonArrayList();
                refreshListView();
            }
        }
    };

    private void refreshListView(){
        mLessonListAdapter = new MyLessonListAdapter(getContext(), R.layout.lesson_item,arrayListLesson);
        listView.setAdapter(mLessonListAdapter);
    }
}
