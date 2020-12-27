package com.example.lingophile.Views;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.lingophile.Models.Lesson;
import com.example.lingophile.Models.User;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private EditText query;
    private Button search;
    private static ArrayList<User> arrayListUser;
    private static ArrayList<Lesson> arrayListLesson;
    private ListView listView;

}
