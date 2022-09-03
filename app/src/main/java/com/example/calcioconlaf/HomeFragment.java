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
import android.widget.ImageButton;

import com.example.calcioconlaf.GameStadium.StadiumThread;
import com.example.calcioconlaf.GameTransfer.TransferThread;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    public String username;
    public String username2;
    String username1;
    public String indexLobby;
    StadiumThread stadiumThread;
    HomeFragment homeFragment=HomeFragment.this;
    TransferThread transferThread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username=getArguments().getString("Username");
        username2=getArguments().getString("UsernameLobby");
        Log.v("Username2","c"+username2);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton imgBtnStadium = getView().findViewById(R.id.imgBtnStadium);
        ImageButton imgBtnTransfer = getView().findViewById(R.id.imgBtnTransfer);

        imgBtnStadium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stadiumThread = new StadiumThread(username, username2, homeFragment);
                stadiumThread.start();
            }
        });

        imgBtnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransferThread transferThread=new TransferThread(username, username2, homeFragment);
                transferThread.start();
            }
        });
        if(username==null){
            username1=username2;
        }else{
            username1=username;
        }
    }
}