package com.example.calcioconlaf;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LobbyThread extends Thread{
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference lobbyStadiumRef = ref.child("LobbyStadium");
    boolean trovati;
    String username;
    String usernameLobby;
    String indexLobby;
    LobbyActivity lobbyActivity;
    ArrayList<Quiz> domande;

    public LobbyThread(String username,String usernameLobby,String indexLobby,LobbyActivity lobbyActivity, ArrayList<Quiz> domande){
        this.username=username;
        this.usernameLobby=usernameLobby;
        this.lobbyActivity=lobbyActivity;
        this.indexLobby=indexLobby;
        this.domande=domande;
    }

    @Override
    public void run() {
        trovati=false;
        lobbyStadiumRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (username == null) {
                    username = usernameLobby;
                }
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.hasChild(username)) {

                        if (ds.getChildrenCount() > 1) {
                            trovati = true;
                        } else {
                            if (snapshot.getChildrenCount() == 1) {
                                lobbyStadiumRef.child("").setValue("");
                                lobbyStadiumRef.child(ds.getKey()).removeValue();
                            } else {
                                lobbyStadiumRef.child(ds.getKey()).removeValue();
                            }
                            lobbyActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent5 = new Intent(lobbyActivity, GameActivity.class);
                                    intent5.putExtra("UsernameLobby", username);
                                    lobbyActivity.startActivity(intent5);
                                }
                            });
                        }
                    }
                }

                if(trovati){
                    DatabaseReference lobbystadium=ref.child("LobbyStadium").child(indexLobby);
                    Boolean primoFiglio=false;
                    lobbystadium.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int postoPrimoFiglio = 0;
                            int tot=(int) snapshot.getChildrenCount();
                            int i=0;
                            for(DataSnapshot ds:snapshot.getChildren()){
                                if(ds.getValue().equals(username)){
                                    postoPrimoFiglio=tot-i;
                                }else{
                                    i++;
                                }
                            }
                            if(postoPrimoFiglio==tot){
                                setPartita();
                            }else {
                                lobbyActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent4=new Intent(lobbyActivity, QuizStadium.class);
                                        intent4.putExtra("Username", username);
                                        intent4.putExtra("IndexLobby", indexLobby);
                                        lobbyActivity.startActivity(intent4);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setPartita(){
        DatabaseReference gamestadium=ref.child("GameStadium");
        gamestadium.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DomandeThread domandeThread = new DomandeThread(lobbyActivity, domande, username, indexLobby);
                SetUtentiThread setUtentiThread=new SetUtentiThread(indexLobby);
                lobbyActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        domandeThread.start();
                        setUtentiThread.start();

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
