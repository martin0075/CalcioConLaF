package com.example.calcioconlaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LobbyActivity extends AppCompatActivity {

    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");

    String username;
    Boolean trovati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        TextView timer=findViewById(R.id.txtTimer);

        Intent intent=getIntent();
        username=intent.getStringExtra("Username");

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("seconds remaining: " + millisUntilFinished / 1000);
                // logic to set the EditText could go here
            }

            public void onFinish() {
                if (utentiTrovati(username)) {
                    Intent intent1=new Intent(LobbyActivity.this, ProvaAPI.class);
                    startActivity(intent1);
                }
                else{
                    Intent intent2=new Intent(LobbyActivity.this, HomeFragment.class);
                    startActivity(intent2);
                }
                timer.setText("done!");
            }

        }.start();
    }

    private boolean utentiTrovati(String username){
        DatabaseReference ref = database.getReference();
        DatabaseReference lobbyStadiumRef = ref.child("LobbyStadium");
        trovati=false;

        lobbyStadiumRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.hasChild(username)){
                        if(ds.getChildrenCount()>1){
                            trovati= true;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return trovati;
    }




}