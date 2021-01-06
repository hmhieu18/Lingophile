package com.example.lingophile.Views;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.lingophile.Helper.TranslateListener;
import com.example.lingophile.Helper.TranslatorHelper;
import com.example.lingophile.R;

public class FlashcardInputDialog extends AppCompatDialogFragment {
    private EditText editTextWord;
    private EditText editTextMeaning;
    private ExampleDialogListener listener;
    private TranslatorHelper translatorHelper = TranslatorHelper.getInstance();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Bundle mArgs = getArguments();
        final int position = mArgs.getInt("position");
        String meaning = mArgs.getString("meaning");
        String word = mArgs.getString("word");
        View view = inflater.inflate(R.layout.flashcard_dialog, null);
        builder.setView(view)
                .setTitle("Word")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String word = editTextWord.getText().toString();
                        String meaning = editTextMeaning.getText().toString();
                        listener.applyTexts(position, word, meaning);
                    }
                });
        editTextWord = view.findViewById(R.id.wordInDialog);
        editTextWord.setText(word);
        editTextMeaning = view.findViewById(R.id.meaningInDialog);
        editTextMeaning.setText(meaning);
        editTextWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    translatorHelper.downloadTranslator(new TranslateListener() {
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

                        }

                        @Override
                        public void onListenTranslateSuccess(String str) {

                        }
                    });
                    translatorHelper.translateText(editTextWord.getText().toString(), new TranslateListener() {
                                ProgressDialog dialog;

                                @Override
                                public void onStart() {
                                    dialog = new ProgressDialog(getActivity());
                                    dialog.setMessage("Please wait...");
                                    dialog.show();
                                }

                                @Override
                                public void onFinish() {
//                        if (dialog.isShowing())
//                            dialog.dismiss();
                                }

                                @Override
                                public void onFail() {
//                        if (dialog.isShowing())
//                            dialog.dismiss();
                                }

                                @Override
                                public void updateUI() {

                                }

                                @Override
                                public void onListenTranslateSuccess(String str) {
                                    editTextMeaning.setText(str);
                                }
                            }
                    );
                    return true;
                }
                return false;
            }
        });
//        editTextMeaning.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                });
//            }
//        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ExampleDialogListener) context;
    }

    public interface ExampleDialogListener {
        void applyTexts(int position, String username, String password);
    }

}