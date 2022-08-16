package com.example.calcioconlaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizStadium extends AppCompatActivity {
    ArrayList<PlayerGame> utenti=new ArrayList<>();
    ArrayList<Quiz> domande;
    QuizStadium quizStadium=QuizStadium.this;

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
        ReadPlayerGameThread readPlayerGameThread=new ReadPlayerGameThread(indexLobby,quizStadium);
        readPlayerGameThread.start();
        PartitaThread partitaThread=new PartitaThread(domande,quizStadium,indexLobby);
        partitaThread.start();
    }
}