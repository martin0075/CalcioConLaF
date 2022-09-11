package com.example.calcioconlaf.GameStadium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.calcioconlaf.GameActivity;
import com.example.calcioconlaf.Login.LoginActivity;
import com.example.calcioconlaf.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuizStadium extends AppCompatActivity {
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    ArrayList<PlayerGame> utenti=new ArrayList<>();
    DatabaseReference ref = database.getReference();
    ArrayList<Quiz> domande;
    QuizStadium quizStadium=QuizStadium.this;
    int numeroGiocatori;
    String indexLobby;
    String username;
    String indice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizstadium);
        Intent intent = getIntent();
        username = intent.getStringExtra("Username");
        indexLobby = intent.getStringExtra("IndexLobby");
        if ((ArrayList<Quiz>) getIntent().getSerializableExtra("Domande") == null) {
            domande = (ArrayList<Quiz>) getIntent().getSerializableExtra("DomandeElse");
        } else {
            domande = (ArrayList<Quiz>) getIntent().getSerializableExtra("Domande");
        }
        ReadPlayerGameThread readPlayerGameThread = new ReadPlayerGameThread(indexLobby, quizStadium, domande, username);
        readPlayerGameThread.start();

    }
    @Override
    public void onBackPressed() {

    }
}