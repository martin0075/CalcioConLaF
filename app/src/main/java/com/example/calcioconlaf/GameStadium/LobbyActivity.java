package com.example.calcioconlaf.GameStadium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.calcioconlaf.HomeFragment;
import com.example.calcioconlaf.R;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class LobbyActivity extends AppCompatActivity {

    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");

    String username;
    Boolean trovati;
    String usernameLobby;
    String indexLobby;
    LobbyActivity lobbyActivity=LobbyActivity.this;
    int a;
    int b;
    int c;
    Random r=new Random();
    ArrayList<Quiz> domande=new ArrayList<Quiz>();
    DomandeThread domandeThread;
    HomeFragment home=new HomeFragment();
    LobbyThread lobbyThread;


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
                lobbyThread=new LobbyThread(username,usernameLobby,indexLobby,lobbyActivity, domande);
                lobbyThread.start();


            }
        }.start();
    }
    @Override
    public void onBackPressed() {

    }
}