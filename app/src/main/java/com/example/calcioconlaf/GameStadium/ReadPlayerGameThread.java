package com.example.calcioconlaf.GameStadium;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.calcioconlaf.GameStadium.PartitaThread;
import com.example.calcioconlaf.GameStadium.PlayerGame;
import com.example.calcioconlaf.GameStadium.Quiz;
import com.example.calcioconlaf.GameStadium.QuizStadium;
import com.example.calcioconlaf.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReadPlayerGameThread extends Thread{
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference gameStadiumRef = ref.child("GameStadium");
    String indexLobby;
    ArrayList<PlayerGame> utentiList=new ArrayList<>();
    QuizStadium quizStadium;
    int i=0;
    int numeroGiocatori;
    String username;
    ArrayList<Quiz> domande;
    public ReadPlayerGameThread(String indexLobby,QuizStadium quizStadium,ArrayList<Quiz> domande,String username) {
        this.indexLobby=indexLobby;
        this.quizStadium=quizStadium;
        this.domande=domande;
        this.username=username;
    }

    @Override
    public void run() {
        super.run();
        leggiUtenti();
    }
    public void leggiUtenti(){
        DatabaseReference utenti=gameStadiumRef.child(indexLobby).child("utenti");
        utenti.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    int score= ((Long) ds.child("score").getValue()).intValue();
                    boolean aiuto4= (boolean) ds.child("aiuto4").getValue();
                    boolean activePlayer= (boolean) ds.child("activePlayer").getValue();
                    boolean aiuto1= (boolean) ds.child("aiuto1").getValue();
                    boolean aiuto2= (boolean) ds.child("aiuto2").getValue();
                    boolean aiuto3= (boolean) ds.child("aiuto3").getValue();
                    String username= (String) ds.child("username").getValue();
                    String rispostaSel= (String) ds.child("rispostaSel").getValue();
                    String stoppato=(String) ds.child("stoppato").getValue();
                    utentiList.add(new PlayerGame(username,aiuto1,aiuto2,aiuto3,aiuto4,score,activePlayer,rispostaSel,stoppato));
                }
                numeroGiocatori=utentiList.size();
                if(utentiList.size()== snapshot.getChildrenCount()){
                    scriviUtenti();
                }
                if(utentiList.size()==2){
                    Log.v("1","1");
                    int n=2;
                    cancellaGiocatori(n);
                }
                if(utentiList.size()==3){
                    Log.v("2","2");
                    int n=1;
                    cancellaGiocatori(n);
                }
                PartitaThread partitaThread=new PartitaThread(quizStadium,indexLobby,username,domande,numeroGiocatori);
                partitaThread.start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void scriviUtenti(){
        for(i=0;i<utentiList.size();i++){
            switch(i){
                case 0:
                    quizStadium.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView user1=quizStadium.findViewById(R.id.txtUser1);
                            TextView punt1=quizStadium.findViewById(R.id.txtAvatar1);
                            user1.setText(utentiList.get(i).getUsername());
                            //punt1.setText(String.valueOf(utentiList.get(i).getScore()));
                        }
                    });
                    break;
                case 1:
                    quizStadium.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView user2=quizStadium.findViewById(R.id.txtUser2);
                            TextView punt2=quizStadium.findViewById(R.id.txtAvatar2);
                            user2.setText(utentiList.get(i).getUsername());
                            //punt2.setText(String.valueOf(utentiList.get(i).getScore()));
                        }
                    });
                    break;
                case 2:
                    quizStadium.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView user3=quizStadium.findViewById(R.id.txtUser3);
                            TextView punt3=quizStadium.findViewById(R.id.txtAvatar3);
                            user3.setText(utentiList.get(i).getUsername());
                            //punt3.setText(String.valueOf(utentiList.get(i).getScore()));
                        }
                    });
                    break;
                case 3:
                    quizStadium.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView user4=quizStadium.findViewById(R.id.txtUser4);
                            TextView punt4=quizStadium.findViewById(R.id.txtAvatar4);
                            user4.setText(utentiList.get(i).getUsername());
                            //punt4.setText(String.valueOf(utentiList.get(i).getScore()));
                        }
                    });
                    break;
            }
        }
    }
    public void cancellaGiocatori(int n){
        if(n==2){
            quizStadium.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //LinearLayout layout3 =quizStadium.findViewById(R.id.vert3);

                    TextView user3=quizStadium.findViewById(R.id.txtUser3);
                    TextView punt3=quizStadium.findViewById(R.id.txtAvatar3);
                    ImageView img3=quizStadium.findViewById(R.id.imgAvatar3);
                    user3.setVisibility(View.INVISIBLE);
                    punt3.setVisibility(View.INVISIBLE);
                    img3.setVisibility(View.INVISIBLE);

                    //LinearLayout layout4 =quizStadium.findViewById(R.id.vert4);
                    TextView user4=quizStadium.findViewById(R.id.txtUser4);
                    TextView punt4=quizStadium.findViewById(R.id.txtAvatar4);
                    ImageView img4=quizStadium.findViewById(R.id.imgAvatar4);
                    user4.setVisibility(View.INVISIBLE);
                    punt4.setVisibility(View.INVISIBLE);
                    img4.setVisibility(View.INVISIBLE);
                }
            });
        }
        if(n==1){
            //LinearLayout layout =quizStadium.findViewById(R.id.vert4);
            TextView user4=quizStadium.findViewById(R.id.txtUser4);
            TextView punt4=quizStadium.findViewById(R.id.txtAvatar4);
            ImageView img4=quizStadium.findViewById(R.id.imgAvatar4);
            user4.setVisibility(View.INVISIBLE);
            punt4.setVisibility(View.INVISIBLE);
            img4.setVisibility(View.INVISIBLE);
        }
    }
}
