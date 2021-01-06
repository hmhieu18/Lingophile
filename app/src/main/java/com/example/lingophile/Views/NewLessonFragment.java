package com.example.lingophile.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lingophile.Adapter.SmallFlashCardListAdapter;
import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.Models.FlashCard;
import com.example.lingophile.Models.Lesson;
import com.example.lingophile.R;

import java.util.ArrayList;
import java.util.Objects;

public class NewLessonFragment extends Fragment implements FlashcardInputDialog.ExampleDialogListener {
    private EditText titleEditText, descriptionEditText;
    private Button addBtn, nextBtn;
    public static ArrayList<FlashCard> flashCardArrayList = new ArrayList<>();
    private ListView flashcardListView;
    public static SmallFlashCardListAdapter smallFlashCardListAdapter;
    private DataCenter dataCenter = DataCenter.getInstance();


    public NewLessonFragment() {
        // Required empty public constructor
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static NewLessonFragment newInstance(String param1, String param2) {
        NewLessonFragment fragment = new NewLessonFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_lesson, container, false);
        initComponent(view);
        return view;
    }

    private ListView.OnItemClickListener itemClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            openDialog(position);
        }
    };
    private ListView.OnItemLongClickListener listViewItemOnLongClick = new ListView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            showOptionDialog(getContext(), i);
            return true;
        }
    };

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    View.OnClickListener addClick = new View.OnClickListener() {
        public void onClick(View v) {
            flashCardArrayList.add(new FlashCard());
            Log.d("xxxxxxxxxxxxxx", Integer.toString(flashCardArrayList.size()));
//            smallFlashCardListAdapter = new SmallFlashCardListAdapter(getContext(), R.layout.lesson_item, flashCardArrayList);
//            flashcardListView.setAdapter(smallFlashCardListAdapter);
            smallFlashCardListAdapter.notifyDataSetChanged();
            openDialog(flashCardArrayList.size() - 1);
        }
    };

    View.OnClickListener nextClick = new View.OnClickListener() {
        public void onClick(View v) {
            openFragment(EditScheduleFragment.newInstance(
                    new Lesson(dataCenter.user.getEmail(), descriptionEditText.getText().toString(),
                            titleEditText.getText().toString(),
                            flashCardArrayList)));
        }
    };

    private void initComponent(View view) {
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        titleEditText = view.findViewById(R.id.titleEditText);
        flashcardListView = view.findViewById(R.id.flashcardListView);
        addBtn = view.findViewById(R.id.newFlashcradBtn);
        addBtn.setOnClickListener(addClick);
        nextBtn = view.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(nextClick);
        smallFlashCardListAdapter = new SmallFlashCardListAdapter(getContext(), R.layout.small_flashcard_item, flashCardArrayList);
        flashcardListView.setAdapter(smallFlashCardListAdapter);
        flashcardListView.setOnItemClickListener(itemClickListener);
        flashcardListView.setOnItemLongClickListener(listViewItemOnLongClick);
    }

    @Override
    public void applyTexts(int position, String word, String meaning) {
        flashCardArrayList.get(position).setWord(word);
        flashCardArrayList.get(position).setMeaning(meaning);
    }

    public void openDialog(int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("word", flashCardArrayList.get(position).getWord());
        args.putString("meaning", flashCardArrayList.get(position).getMeaning());
        FlashcardInputDialog flashcardInputDialog = new FlashcardInputDialog();
        flashcardInputDialog.setArguments(args);
        flashcardInputDialog.show(getChildFragmentManager(), Integer.toString(position));
    }

    private void showOptionDialog(final Context context, final int position) {
        final CharSequence[] options = {"Remove this card", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Option");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Remove this card")) {
                    removeFlashcardByPosition(position);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void removeFlashcardByPosition(int position) {
        flashCardArrayList.remove(position);
        smallFlashCardListAdapter.notifyDataSetChanged();
    }
}