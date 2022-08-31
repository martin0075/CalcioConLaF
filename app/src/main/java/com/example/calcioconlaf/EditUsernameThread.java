package com.example.calcioconlaf;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditUsernameThread extends Thread{
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference usersRef = ref.child("Users");
    DatabaseReference leaderbordStadiumRef=ref.child("LeaderBoardStadium");
    String username;
    String newUsername;


    public EditUsernameThread(String username, String newUsername) {
        this.username = username;
        this.newUsername=newUsername;

    }

    @Override
    public void run() {
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(username.equals(ds.child("Username").getValue().toString())){
                        usersRef.child(ds.getKey()).child("Username").setValue(newUsername);

                    }

                    /*for(DataSnapshot d1:ds.getChildren()){
                        Log.v("LOG1", d1.child("Username").getValue().toString());
                    }*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        leaderbordStadiumRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.getKey().equals(username)){
                        String val=ds.getValue().toString();

                    }


                    /*for(DataSnapshot d1:ds.getChildren()){
                        Log.v("LOG1", d1.child("Username").getValue().toString());
                    }*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
