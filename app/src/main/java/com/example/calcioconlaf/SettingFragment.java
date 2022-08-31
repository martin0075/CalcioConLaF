package com.example.calcioconlaf;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.calcioconlaf.GameStadium.StadiumThread;
import com.example.calcioconlaf.GameTransfer.TransferThread;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {


    public String username1;
    public String newUsername;
    public String newPassword;
    private EditUsernameThread editUsername;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username1=getArguments().getString("Username");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnEditUsername=getView().findViewById(R.id.btnEditUsername);
        Button btnEditPassword=getView().findViewById(R.id.btnEditPassword);
        EditText username=getView().findViewById(R.id.txtEditUsername);
        EditText password=getView().findViewById(R.id.txtEditPassword);
        EditText title=getView().findViewById(R.id.txtTileUser);
        title.setText(username1);

        btnEditUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().length()>0){
                    newUsername=username.getText().toString();
                    editUsername=new EditUsernameThread(username1, newUsername);
                    editUsername.start();
                }
            }
        });

        btnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().length()>0){
                    newPassword=password.getText().toString();
                }
            }
        });
    }
}