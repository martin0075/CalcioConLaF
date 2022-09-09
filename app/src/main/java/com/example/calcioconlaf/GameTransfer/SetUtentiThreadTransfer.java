package com.example.calcioconlaf.GameTransfer;

import androidx.annotation.NonNull;

import com.example.calcioconlaf.GameTransfer.PlayerGameTransfer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SetUtentiThreadTransfer extends Thread{
    String indexLobby;
    String username;
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    ArrayList<PlayerGameTransfer> utenti=new ArrayList<>();

    public SetUtentiThreadTransfer(String indexLobby, String username) {
        this.indexLobby = indexLobby;
        this.username = username;
    }

    @Override
    public void run() {
        super.run();
        leggiUtenti();
    }
    public void leggiUtenti(){
        DatabaseReference lobbyTransfer=ref.child("LobbyTransfer").child(indexLobby);
        lobbyTransfer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int postoPrimoFiglio = 0;
                int tot=(int) snapshot.getChildrenCount();
                int i=0;
                for(DataSnapshot ds:snapshot.getChildren()){
                    utenti.add(new PlayerGameTransfer(ds.getKey(),false,false,false,false, 0,false,"","null"));
                    if(ds.getValue().equals(username)){
                        postoPrimoFiglio=tot-i;
                    }else{
                        i++;
                    }
                }
                if(postoPrimoFiglio==tot){
                    PlayerGameTransfer utente=utenti.get(0);
                    utente.setActivePlayer(true);
                }
                if(utenti.size()==snapshot.getChildrenCount()){
                    scriviUtenti();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void scriviUtenti(){
        DatabaseReference gameTransfer=ref.child("GameTransfer").child(indexLobby);
        gameTransfer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gameTransfer.child("utenti").setValue(utenti);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
