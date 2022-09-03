package com.example.calcioconlaf;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.calcioconlaf.Login.EditPasswordThread;
import com.example.calcioconlaf.Login.EditUsernameThread;
import com.example.calcioconlaf.Login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {


    public String username1;
    String username2;
    String username;
    SettingFragment settingFragment=SettingFragment.this;
    GameActivity gameActivity= (GameActivity) settingFragment.getActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username1=getArguments().getString("Username");
        username2=getArguments().getString("UsernameLobby");
        Bundle bundle=new Bundle();
        if(username1==null){
            username=username2;
        }else{
            username=username1;
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button feedback=getActivity().findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingFragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent feedint=new Intent(getActivity(),FeedbackActivity.class);
                        feedint.putExtra("UsernameFeed",username);
                        feedint.putExtra("Activity", "com.example.calcioconlaf.GameActivity");
                        settingFragment.getActivity().startActivity(feedint);
                    }
                });
            }
        });
        Button about=getActivity().findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingFragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent aboutInt=new Intent(settingFragment.getActivity(),AboutActivity.class);
                        settingFragment.getActivity().startActivity(aboutInt);
                    }
                });
            }
        });
        Button version=getActivity().findViewById(R.id.version);
        version.setText("Version 1.0.0");
        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button logout=getActivity().findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingFragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent logout=new Intent(settingFragment.getActivity(), LoginActivity.class);
                        settingFragment.getActivity().startActivity(logout);
                    }
                });
            }
        });
    }
}