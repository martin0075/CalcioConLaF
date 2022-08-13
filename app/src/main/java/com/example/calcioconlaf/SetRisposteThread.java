package com.example.calcioconlaf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class SetRisposteThread extends Thread{

    ArrayList<Quiz> domande;
    ArrayList<String> opzioni;
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference gameStadiumRef = ref.child("GameStadium");
    LobbyActivity lobbyActivity;
    String username;
    String indexLobby;

    public SetRisposteThread(ArrayList<Quiz> domande, ArrayList<String> opzioni,LobbyActivity lobbyActivity,String username,String indexLobby) {
        this.domande=domande;
        this.opzioni=opzioni;
        this.lobbyActivity=lobbyActivity;
        this.username=username;
        this.indexLobby=indexLobby;
    }

    @Override
    public void run() {
        super.run();
        addOption();
        write();
    }

    public void addOption() {
        Log.v("size1", String.valueOf(opzioni.size()));
        int cont=0;
        for (int i = 0; i < domande.size(); i++) {
            for (int a = 0; a < 3; a++) {
                if ((!(domande.get(i).getOption1().equals(opzioni.get(cont))))
                        && (!(domande.get(i).getOption2().equals(opzioni.get(cont))))
                        && (!(domande.get(i).getOption3().equals(opzioni.get(cont)))
                        && (!(domande.get(i).getOption4().equals(opzioni.get(cont)))))) {
                    switch (a) {
                        case 0:
                            domande.get(i).setOption1(opzioni.get(cont));
                            opzioni.remove(cont);
                            if(cont!=0){
                                cont=0;
                            }
                            break;
                        case 1:
                            domande.get(i).setOption2(opzioni.get(cont));
                            opzioni.remove(cont);
                            if(cont!=0){
                                cont=0;
                            }
                            break;
                        case 2:
                            domande.get(i).setOption3(opzioni.get(cont));
                            opzioni.remove(cont);
                            if(cont!=0){
                                cont=0;
                            }
                            break;
                    }
                }
                else{
                    cont++;
                    a--;
                }

            }
        }
    }
    public void write(){
        gameStadiumRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gameStadiumRef.child(indexLobby).child("domande").setValue(domande);
                lobbyActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent4=new Intent(lobbyActivity, QuizStadium.class);
                        intent4.putExtra("Username", username);
                        intent4.putExtra("IndexLobby", indexLobby);
                        intent4.putExtra("Domande",domande);

                        lobbyActivity.startActivity(intent4);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
