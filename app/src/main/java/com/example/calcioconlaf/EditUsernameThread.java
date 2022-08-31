package com.example.calcioconlaf;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.calcioconlaf.Login.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditUsernameThread extends Thread{
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference usersRef = ref.child("Users");
    DatabaseReference leaderbordStadiumRef=ref.child("LeaderBoardStadium");
    String username;
    String newUsername;
    SettingFragment settingFragment;
    EditText title;


    public EditUsernameThread(String username, String newUsername,  SettingFragment settingFragment, EditText title) {
        this.username = username;
        this.newUsername=newUsername;
        this.settingFragment=settingFragment;
        this.title=title;

    }

    @Override
    public void run() {
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> usernamePresenti=new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    usernamePresenti.add(ds.child("Username").getValue().toString());

                }
                if(usernamePresenti.size()==snapshot.getChildrenCount()){
                    if(!usernamePresenti.contains(newUsername)){
                        for(DataSnapshot ds: snapshot.getChildren()){
                            if(username.equals(ds.child("Username").getValue().toString())){
                                usersRef.child(ds.getKey()).child("Username").setValue(newUsername);
                                title.setText(newUsername);
                                settingFragment.getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(settingFragment.getActivity(), "Username modificato correttamente",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                leaderbordStadiumRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Log.v("user", username);
                                        for (DataSnapshot ds : snapshot.getChildren()) {

                                            if(ds.getKey().equals(username)){
                                                String val=ds.getValue().toString();
                                                leaderbordStadiumRef.child(ds.getKey()).removeValue();
                                                leaderbordStadiumRef.child(newUsername).setValue(val);

                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    }
                    else{
                        Toast.makeText(settingFragment.getActivity(), "Username già presente",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
