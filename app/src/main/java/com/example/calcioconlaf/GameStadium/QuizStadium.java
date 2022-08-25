package com.example.calcioconlaf.GameStadium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.calcioconlaf.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuizStadium extends AppCompatActivity {
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    ArrayList<PlayerGame> utenti=new ArrayList<>();
    ArrayList<Quiz> domande;
    QuizStadium quizStadium=QuizStadium.this;
    int numeroGiocatori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizstadium);
        Intent intent=getIntent();
        String username=intent.getStringExtra("Username");
        String indexLobby=intent.getStringExtra("IndexLobby");
        if((ArrayList<Quiz>) getIntent().getSerializableExtra("Domande")==null){
            domande=(ArrayList<Quiz>) getIntent().getSerializableExtra("DomandeElse");
        }else{
            domande=(ArrayList<Quiz>) getIntent().getSerializableExtra("Domande");
        }
        ReadPlayerGameThread readPlayerGameThread=new ReadPlayerGameThread(indexLobby,quizStadium,domande,username);
        readPlayerGameThread.start();

    }
}