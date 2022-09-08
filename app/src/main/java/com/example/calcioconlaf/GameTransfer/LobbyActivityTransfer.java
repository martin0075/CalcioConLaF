package com.example.calcioconlaf.GameTransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.calcioconlaf.HomeFragment;
import com.example.calcioconlaf.R;
import com.google.firebase.database.FirebaseDatabase;

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










}