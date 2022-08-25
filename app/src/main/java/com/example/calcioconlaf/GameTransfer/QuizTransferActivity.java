package com.example.calcioconlaf.GameTransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.calcioconlaf.GameStadium.PlayerGame;
import com.example.calcioconlaf.GameStadium.Quiz;
import com.example.calcioconlaf.GameStadium.QuizStadium;
import com.example.calcioconlaf.GameStadium.ReadPlayerGameThread;
import com.example.calcioconlaf.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuizTransferActivity extends AppCompatActivity {
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    ArrayList<PlayerGame> utenti=new ArrayList<>();
    ArrayList<QuizTransfer> domande;
    QuizTransferActivity quizTransferActivity=QuizTransferActivity.this;
    int numeroGiocatori;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_transfer);
        Intent intent=getIntent();
        String username=intent.getStringExtra("Username");
        String indexLobby=intent.getStringExtra("IndexLobby");
        if((ArrayList<QuizTransfer>) getIntent().getSerializableExtra("Domande")==null){
            domande=(ArrayList<QuizTransfer>) getIntent().getSerializableExtra("DomandeElse");
        }else{
            domande=(ArrayList<QuizTransfer>) getIntent().getSerializableExtra("Domande");
        }
        //ReadPlayerGameThread readPlayerGameThread=new ReadPlayerGameThread(indexLobby,quizStadium,domande,username);
        //readPlayerGameThread.start();
    }
}