package com.example.calcioconlaf.GameTransfer;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LobbyTransferThread extends Thread{
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference lobbyTransferRef = ref.child("LobbyTransfer");
    boolean trovati;
    String username;
    String usernameLobby;
    String indexLobby;
    LobbyActivityTransfer lobbyActivity;
    ArrayList<QuizTransfer> domande;

    public LobbyTransferThread(String username, String usernameLobby, String indexLobby, LobbyActivityTransfer lobbyActivity, ArrayList<QuizTransfer> domande) {
        this.username = username;
        this.usernameLobby = usernameLobby;
        this.indexLobby = indexLobby;
        this.lobbyActivity = lobbyActivity;
        this.domande = domande;
    }

    @Override
    public void run() {
        trovati=false;
        lobbyTransferRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                lobbyTransferRef.child("").setValue("");
                                lobbyTransferRef.child(ds.getKey()).removeValue();
                            } else {
                                lobbyTransferRef.child(ds.getKey()).removeValue();
                            }
                            lobbyActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent5 = new Intent(lobbyActivity, GameActivityTransfer.class);
                                    intent5.putExtra("UsernameLobby", username);
                                    lobbyActivity.startActivity(intent5);
                                }
                            });
                        }
                    }
                }
                if (trovati) {
                    DatabaseReference lobbyTransfer = ref.child("LobbyTransfer").child(indexLobby);
                    lobbyTransfer.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int postoPrimoFiglio = 0;
                            int tot = (int) snapshot.getChildrenCount();
                            int i = 0;
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                if (ds.getValue().equals(username)) {
                                    postoPrimoFiglio = tot - i;
                                } else {
                                    i++;
                                }
                            }
                            if (postoPrimoFiglio == tot) {
                                setPartita();
                            } else {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        lobbyActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent4=new Intent(lobbyActivity, QuizTransferActivity.class);
                                                intent4.putExtra("Username", username);
                                                intent4.putExtra("IndexLobby", indexLobby);
                                                DatabaseReference game=ref.child("GameTransfer").child(indexLobby).child("domande");
                                                game.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for(DataSnapshot ds:snapshot.getChildren()){
                                                            String id= String.valueOf(ds.child("id").getValue());
                                                            String url= (String) ds.child("urlImage").getValue();
                                                            String domanda= (String) ds.child("domanda").getValue();
                                                            String option3= (String) ds.child("option3").getValue();
                                                            String option4= (String) ds.child("option4").getValue();
                                                            String option2= (String) ds.child("option2").getValue();
                                                            String option1= (String) ds.child("option1").getValue();
                                                            String answer= (String) ds.child("answer").getValue();
                                                            String city= (String) ds.child("city").getValue();
                                                            String country= (String) ds.child("country").getValue();
                                                            String idTeam= (String) ds.child("idTeam").getValue();
                                                            domande.add(new QuizTransfer(url,option1,option2,option3,option4,answer,domanda,id,city,country,idTeam));
                                                        }
                                                        if(domande.size()==10){
                                                            Log.v("logaaa",domande.get(0).getAnswer());
                                                            intent4.putExtra("DomandeElse",domande);
                                                            lobbyActivity.startActivity(intent4);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            }
                                        });
                                    }
                                },20000);
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
        DatabaseReference gameTransfer=ref.child("GameTransfer");
        gameTransfer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FotoThreadTransfer fotoThreadTransfer = new FotoThreadTransfer(lobbyActivity, domande, username, indexLobby);
                SetUtentiThreadTransfer setUtentiThread=new SetUtentiThreadTransfer(indexLobby,username);
                SetButtonThreadTransfer buttonThread=new SetButtonThreadTransfer(indexLobby);
                lobbyActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fotoThreadTransfer.start();
                        setUtentiThread.start();
                        buttonThread.start();

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
