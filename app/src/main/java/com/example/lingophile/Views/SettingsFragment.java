package com.example.lingophile.Views;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.lingophile.Helper.ReminderHelper;
import com.example.lingophile.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    private Button _logOutButton;
    private Button _aboutUsButton;
    private Button _changePasswordButton;
    private Button _viewProfileButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initComponent(view);
        return view;
    }

    private void initComponent(View view) {
        _logOutButton = view.findViewById(R.id.settings_log_out_button);
        _aboutUsButton =view.findViewById(R.id.settings_about_us_button);
        _changePasswordButton = view.findViewById(R.id.settings_change_password_button);
        _viewProfileButton =view.findViewById(R.id.settings_profile_button);
        LogOutButtonOnclicked();
        AboutUsButtonOnclicked();
        ViewProfileOnClicked();
    }

    private void LogOutButtonOnclicked() {
        _logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getWarningDialog().show();
                FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        //Make sure the user cannot go back
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
            }
        });
    }

    private void ViewProfileOnClicked()
    {
        _viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                startActivity(intent);
            }
        });
    }
    private void AboutUsButtonOnclicked()
    {
        _aboutUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }
//    private AlertDialog.Builder getWarningDialog() {
//        return new AlertDialog.Builder(getContext())
//                .setTitle("Delete reminder")
//                .setMessage("Do you want to remove all of your watering reminder?")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        for (Plant p : AppData.user.userPlants) {
//                            ReminderHelper.deleteEvent(Objects.requireNonNull(getActivity()), p.wateringSchedule.eventID);
//                        }
//                        FirebaseAuth.getInstance().signOut();
//                        Intent intent = new Intent(getActivity(), LoginActivity.class);
//                        startActivity(intent);
//                        //Make sure the user cannot go back
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                    }
//                })
//                .setNegativeButton("No", null)
//                .setIcon(android.R.drawable.ic_dialog_alert);
//    }
}