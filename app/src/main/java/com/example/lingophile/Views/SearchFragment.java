package com.example.lingophile.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lingophile.Adapter.MyLessonListAdapter;
import com.example.lingophile.Adapter.UserAdapter;
import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.Helper.ReadDataListener;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.User;
import com.example.lingophile.R;

import java.util.ArrayList;
import java.util.Objects;

public class SearchFragment extends Fragment {
    private EditText query;
    private Button search;
    private Spinner spinner;
    private static ArrayList<User> arrayListUser;
    private static ArrayList<Lesson> arrayListLesson;
    private ListView listView;
    private FirebaseManagement fm = FirebaseManagement.getInstance();
    private MyLessonListAdapter mLessonListAdapter;
    private UserAdapter mUserAdater;
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
        spinner = view.findViewById(R.id.spinner);
        listView.setOnItemClickListener(itemClickListenerLesson);
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private ListView.OnItemClickListener itemClickListenerLesson = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = null;
            if (listView.getAdapter().equals(mUserAdater)){
                //Search User
                intent = new Intent(getActivity(), UserInfoActivity.class);
                intent.putExtra("author", arrayListUser.get(i));
            } else {
                //Search lesson
                intent = new Intent(getActivity(), LessonViewActivity.class);
                intent.putExtra("lesson", arrayListLesson.get(i));
            }
            Objects.requireNonNull(getActivity()).startActivity(intent);
        }
    };



    View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String text = query.getText().toString();
            Log.d("@@@", "OKKKK");
            if (text != null){
                if (String.valueOf(spinner.getSelectedItem()).equals("Lessons")) {
                    fm.requestLessonSearch(text, new ReadDataListener() {
                        ProgressDialog dialog;
                        @Override
                        public void onStart() {
                            dialog = new ProgressDialog(getActivity());
                            dialog.setMessage("Please wait...");
                            dialog.show();
                        }

                        @Override
                        public void onFinish() {
                            if (dialog.isShowing())
                                dialog.dismiss();
                            refreshListView();
                        }

                        @Override
                        public void onFail() {
                            if (dialog.isShowing())
                                dialog.dismiss();
                        }

                        @Override
                        public void updateUI() {

                        }

                        @Override
                        public void onListenLessonSuccess(Lesson lesson) {

                        }

                    });
                } else {
                    fm.requestUserSearch(text, new ReadDataListener() {
                        ProgressDialog dialog;
                        @Override
                        public void onStart() {
                            dialog = new ProgressDialog(getActivity());
                            dialog.setMessage("Please wait...");
                            dialog.show();
                        }

                        @Override
                        public void onFinish() {
                            if (dialog.isShowing())
                                dialog.dismiss();
                            refreshListView();
                        }

                        @Override
                        public void onFail() {
                            if (dialog.isShowing())
                                dialog.dismiss();
                        }

                        @Override
                        public void updateUI() {

                        }

                        @Override
                        public void onListenLessonSuccess(Lesson lesson) {

                        }
                    });
                }
            }
        }
    };

    private void refreshListView(){
        arrayListLesson = DataCenter.getInstance().getLessonArrayList();
        mLessonListAdapter = new MyLessonListAdapter(getContext(), R.layout.lesson_item,arrayListLesson);
        arrayListUser = DataCenter.getInstance().getUserArrayList();
        mUserAdater = new UserAdapter(getContext(), R.layout.author_info_item, arrayListUser);
        if (String.valueOf(spinner.getSelectedItem()).equals("Authors")){
            listView.setAdapter(mUserAdater);
        } else {
            listView.setAdapter(mLessonListAdapter);
        }
    }
}
