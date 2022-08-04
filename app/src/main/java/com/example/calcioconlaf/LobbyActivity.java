package com.example.calcioconlaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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
    String usernameLobby;
    String indexLobby;
    HomeFragment home=new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        TextView timer=findViewById(R.id.txtTimer);

        Intent intent=getIntent();
        username=intent.getStringExtra("Username");
        Intent intent2=getIntent();
        usernameLobby=intent2.getStringExtra("UsernameLobby");
        Intent intent3=getIntent();
        indexLobby=intent3.getStringExtra("IndexLobby");



        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("seconds remaining: " + millisUntilFinished / 1000);
                // logic to set the EditText could go here
            }

            public void onFinish() {
                DatabaseReference ref = database.getReference();
                DatabaseReference lobbyStadiumRef = ref.child("LobbyStadium");
                trovati=false;
                lobbyStadiumRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(username==null){
                         username=usernameLobby;
                        }
                        for(DataSnapshot ds: snapshot.getChildren()){
                            Log.d("Bella3", String.valueOf(ds.hasChild(username)));
                            if(ds.hasChild(username)){
                                Log.d("Bella2",String.valueOf(ds.getChildrenCount()));
                                if(ds.getChildrenCount()>1){
                                    trovati= true;
                                    Intent intent4=new Intent(LobbyActivity.this, QuizStadium.class);
                                    intent4.putExtra("Username", username);
                                    intent4.putExtra("IndexLobby", indexLobby);
                                    startActivity(intent4);
                                }else{
                                    if(snapshot.getChildrenCount()==1){
                                        lobbyStadiumRef.child("").setValue("");
                                        lobbyStadiumRef.child(ds.getKey()).removeValue();
                                    }else{
                                        lobbyStadiumRef.child(ds.getKey()).removeValue();
                                    }
                                    Intent intent5=new Intent(LobbyActivity.this, GameActivity.class);
                                    intent5.putExtra("UsernameLobby",username);
                                    startActivity(intent5);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }.start();
    }

    public void setGame(){
        DatabaseReference ref = database.getReference();
        DatabaseReference gameStadiumRef = ref.child("GameStadium");

        for(int i=0; i<10; i++){

        }


    }
}