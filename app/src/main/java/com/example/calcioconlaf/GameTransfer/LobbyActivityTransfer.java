package com.example.calcioconlaf.GameTransfer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.example.calcioconlaf.GameActivity;
import com.example.calcioconlaf.HomeFragment;
import com.example.calcioconlaf.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class LobbyActivityTransfer extends AppCompatActivity {

    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");

    String usernameTransfer;
    Boolean trovati;
    String usernameLobbyTransfer;
    String indexLobbyTransfer;
    LobbyActivityTransfer lobbyActivity=LobbyActivityTransfer.this;
    Random r=new Random();
    String indice;
    ArrayList<QuizTransfer> domande=new ArrayList<QuizTransfer>();
    HomeFragment home=new HomeFragment();
    LobbyTransferThread lobbyTransferThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        TextView timer=findViewById(R.id.txtTimer);

        Intent intent=getIntent();
        usernameTransfer=intent.getStringExtra("Username");
        Intent intent2=getIntent();
        usernameLobbyTransfer=intent2.getStringExtra("UsernameLobby");
        Intent intent3=getIntent();
        indexLobbyTransfer=intent3.getStringExtra("IndexLobby");

        //domandeThread=new DomandeThread(lobbyActivity, domande, username, indexLobby);


        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("secondi rimanenti: " + millisUntilFinished / 1000);
                // logic to set the EditText could go here
            }

            public void onFinish() {
                timer.setText("");
                TextView text=findViewById(R.id.txtAttendPlayers);
                text.setText("Partita in caricamento...");
                lobbyTransferThread=new LobbyTransferThread(usernameTransfer,usernameLobbyTransfer,indexLobbyTransfer,lobbyActivity, domande);
                lobbyTransferThread.start();


            }
        }.start();
    }
    @Override
    protected void onStop() {
        Log.v("STOP","STOP");
        super.onStop();
        DatabaseReference ref=database.getReference();
        DatabaseReference utentiRef = ref.child("GameStadium").child(indexLobbyTransfer).child("utenti");
        utentiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    if(ds.child("username").getValue().equals(usernameLobbyTransfer)){
                        indice=ds.getKey();
                        utentiRef.child(indice).child("stoppato").setValue("true");
                        //Intent intent = new Intent(quizStadium, GameActivity.class);
                        //intent.putExtra("Username", username);
                        //quizStadium.startActivity(intent);
                    }else{
                        indice=ds.getKey();
                        utentiRef.child(indice).child("stoppato").setValue("false");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(lobbyActivity, GameActivity.class);
        intent.putExtra("Username", usernameLobbyTransfer);
        lobbyActivity.startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseReference ref=database.getReference();
        DatabaseReference utentiRef = ref.child("GameStadium").child(indexLobbyTransfer).child("utenti");
        utentiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    if(ds.child("username").getValue().equals(usernameLobbyTransfer)){
                        indice=ds.getKey();
                        utentiRef.child(indice).child("stoppato").setValue("true");
                        //Intent intent = new Intent(quizStadium, GameActivity.class);
                        //intent.putExtra("Username", username);
                        //quizStadium.startActivity(intent);
                    }else{
                        indice=ds.getKey();
                        utentiRef.child(indice).child("stoppato").setValue("false");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public void onBackPressed() {

    }
}