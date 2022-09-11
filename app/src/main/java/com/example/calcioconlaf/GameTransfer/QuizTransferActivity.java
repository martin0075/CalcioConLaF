package com.example.calcioconlaf.GameTransfer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;

import com.example.calcioconlaf.GameActivity;
import com.example.calcioconlaf.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;

public class QuizTransferActivity extends AppCompatActivity{
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    ArrayList<PlayerGameTransfer> utenti=new ArrayList<>();
    ArrayList<QuizTransfer> domande;
    QuizTransferActivity quizTransferActivity=QuizTransferActivity.this;
    int numeroGiocatori;
    String indexLobby;
    String username;
    String indice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_transfer);
        Intent intent=getIntent();
        username=intent.getStringExtra("Username");
        indexLobby=intent.getStringExtra("IndexLobby");
        if((ArrayList<QuizTransfer>) getIntent().getSerializableExtra("Domande")==null){
            domande=(ArrayList<QuizTransfer>) getIntent().getSerializableExtra("DomandeElse");
        }else{
            domande=(ArrayList<QuizTransfer>) getIntent().getSerializableExtra("Domande");
        }
        Log.v("activity","activity");
        ReadPlayerGameThreadTransfer readPlayerGameThread=new ReadPlayerGameThreadTransfer(indexLobby,quizTransferActivity,domande,username);
        readPlayerGameThread.start();
    }
    
    /*@Override
    protected void onStop() {
        Log.v("STOP","STOP");
        super.onStop();
        DatabaseReference ref=database.getReference();
        DatabaseReference utentiRef = ref.child("GameTransfer").child(indexLobby).child("utenti");
        utentiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    if(ds.child("username").getValue().equals(username)){
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
        Intent intent = new Intent(quizTransferActivity, GameActivity.class);
        intent.putExtra("Username", username);
        quizTransferActivity.startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseReference ref=database.getReference();
        DatabaseReference utentiRef = ref.child("GameTransfer").child(indexLobby).child("utenti");
        utentiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    if(ds.child("username").getValue().equals(username)){
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

    }*/
    @Override
    public void onBackPressed() {

    }

}