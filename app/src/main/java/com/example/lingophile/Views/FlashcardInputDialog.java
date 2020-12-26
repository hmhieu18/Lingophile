package com.example.lingophile.Views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.lingophile.R;

public class FlashcardInputDialog extends AppCompatDialogFragment {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Bundle mArgs = getArguments();
        final int position = mArgs.getInt("position");
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
                        String username = editTextUsername.getText().toString();
                        String password = editTextPassword.getText().toString();
                        listener.applyTexts(position, username, password);
                    }
                });
        editTextUsername = view.findViewById(R.id.wordInDialog);
        editTextPassword = view.findViewById(R.id.meaningInDialog);
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