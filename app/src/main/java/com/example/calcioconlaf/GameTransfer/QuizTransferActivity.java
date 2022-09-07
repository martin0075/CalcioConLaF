package com.example.calcioconlaf.GameTransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;

import com.example.calcioconlaf.R;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizTransferActivity extends AppCompatActivity{
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    ArrayList<PlayerGameTransfer> utenti=new ArrayList<>();
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
        Log.v("activity","activity");
        ReadPlayerGameThreadTransfer readPlayerGameThread=new ReadPlayerGameThreadTransfer(indexLobby,quizTransferActivity,domande,username);
        readPlayerGameThread.start();
    }

}