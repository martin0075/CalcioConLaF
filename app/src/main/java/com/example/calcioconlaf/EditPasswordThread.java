package com.example.calcioconlaf;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditPasswordThread extends Thread{
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference usersRef = ref.child("Users");
    String username;
    String newPassword;
    SettingFragment settingFragment;


    public EditPasswordThread(String username, String newPassword, SettingFragment settingFragment) {
        this.username = username;
        this.newPassword=newPassword;
        this.settingFragment=settingFragment;

    }

    @Override
    public void run() {
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(username.equals(ds.child("Username").getValue().toString())){
                        usersRef.child(ds.getKey()).child("Password").setValue(newPassword);
                        settingFragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(settingFragment.getActivity(), "Password modificata correttamente",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
