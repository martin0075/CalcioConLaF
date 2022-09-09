package com.example.calcioconlaf.Login;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.calcioconlaf.Login.EditActivity;
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
    DatabaseReference leaderboardTransferRef=ref.child("LeaderBoardTransfer");
    DatabaseReference recensioniRef=ref.child("Recensioni");
    String username;
    String newUsername;
    EditActivity editActivity;
    TextView title;


    public EditUsernameThread(String username, String newUsername, EditActivity editActivity, TextView title) {
        this.username = username;
        this.newUsername=newUsername;
        this.editActivity=editActivity;
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
                                editActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=new Intent(editActivity,LoginActivity.class);
                                        editActivity.startActivity(intent);
                                        Toast.makeText(editActivity, "Username modificato correttamente",Toast.LENGTH_SHORT).show();;
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
                                leaderboardTransferRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Log.v("user", username);
                                        for (DataSnapshot ds : snapshot.getChildren()) {

                                            if(ds.getKey().equals(username)){
                                                String val=ds.getValue().toString();
                                                leaderboardTransferRef.child(ds.getKey()).removeValue();
                                                leaderboardTransferRef.child(newUsername).setValue(val);

                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                recensioniRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            if(ds.getKey().equals(username)){
                                                float punteggio= Float.parseFloat(String.valueOf(ds.child("punteggio").getValue()));
                                                String recensione=ds.child("recensione").getValue().toString();
                                                recensioniRef.child(ds.getKey()).removeValue();
                                                recensioniRef.child(newUsername).child("punteggio").setValue(punteggio);
                                                recensioniRef.child(newUsername).child("recensione").setValue(recensione);

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
                        Toast.makeText(editActivity, "Username già presente",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
