package com.example.calcioconlaf.GameStadium;

import androidx.annotation.NonNull;

import com.example.calcioconlaf.GameStadium.PlayerGame;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SetUtentiThread extends Thread{
    String indexLobby;
    String username;
    ArrayList<PlayerGame> utenti=new ArrayList<>();
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    public SetUtentiThread(String indexLobby,String username) {
        this.username=username;
        this.indexLobby=indexLobby;
    }

    @Override
    public void run() {
        super.run();
        leggiUtenti();
    }
    public void leggiUtenti(){
        DatabaseReference lobbyStadium=ref.child("LobbyStadium").child(indexLobby);
        lobbyStadium.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int postoPrimoFiglio = 0;
                int tot=(int) snapshot.getChildrenCount();
                int i=0;
                for(DataSnapshot ds:snapshot.getChildren()){
                    utenti.add(new PlayerGame(ds.getKey(),false,false,false,false, 0,false,"","null"));
                    if(ds.getValue().equals(username)){
                        postoPrimoFiglio=tot-i;
                    }else{
                        i++;
                    }
                }
                if(postoPrimoFiglio==tot){
                    PlayerGame utente=utenti.get(0);
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
        DatabaseReference gamestadium=ref.child("GameStadium").child(indexLobby);
        gamestadium.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gamestadium.child("utenti").setValue(utenti);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
