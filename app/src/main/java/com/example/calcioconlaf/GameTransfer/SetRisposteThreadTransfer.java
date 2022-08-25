package com.example.calcioconlaf.GameTransfer;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.calcioconlaf.GameStadium.LobbyActivity;
import com.example.calcioconlaf.GameStadium.Quiz;
import com.example.calcioconlaf.GameStadium.QuizStadium;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SetRisposteThreadTransfer extends Thread {
    ArrayList<QuizTransfer> domande;
    ArrayList<String> opzioni;
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference gameTransfer = ref.child("GameTransfer");
    LobbyActivityTransfer lobbyActivity;
    String username;
    String indexLobby;
    public SetRisposteThreadTransfer(ArrayList<QuizTransfer> domande, ArrayList<String> opzioni,LobbyActivityTransfer lobbyActivity,String username,String indexLobby) {
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
    public void addOption(){
        int cont=0;
        for (int i = 0; i < domande.size(); i++) {
            ArrayList<String> opzioniShuffle=new ArrayList<>();
            opzioniShuffle.add(domande.get(i).getAnswer());
            for (int a = 0; a < 3; a++) {
                opzioniShuffle.add(opzioni.get(cont));
                opzioni.remove(cont);
                if(cont!=0){
                    cont=0;
                }
            }
            Collections.shuffle(opzioniShuffle);
            for(int x=0;x<4;x++) {
                switch (x) {
                    case 0:
                        domande.get(i).setOption1(opzioniShuffle.get(x));
                        break;
                    case 1:
                        domande.get(i).setOption2(opzioniShuffle.get(x));
                        break;
                    case 2:
                        domande.get(i).setOption3(opzioniShuffle.get(x));
                        break;
                    case 3:
                        domande.get(i).setOption4(opzioniShuffle.get(x));
                        break;
                }
            }
        }
    }
    public void write(){
        gameTransfer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gameTransfer.child(indexLobby).child("domande").setValue(domande);
                lobbyActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent4=new Intent(lobbyActivity, QuizTransferActivity.class);
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
