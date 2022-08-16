package com.example.calcioconlaf;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PartitaThread extends Thread{
    ArrayList<Quiz> domande=new ArrayList<>();
    QuizStadium quizStadium;
    String indexLobby;
    int i;
    int cont=0;
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();

    public PartitaThread(ArrayList<Quiz> domande,QuizStadium quizStadium,String indexLobby) {
        this.domande=domande;
        this.quizStadium=quizStadium;
        this.indexLobby=indexLobby;
    }

    @Override
    public void run() {
        super.run();
        game();

    }
    public void game(){
        quizStadium.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                while(cont < domande.size()) {
                    check();
                    Log.v("2","2");
                    String url = domande.get(i).getUrlImage();
                    ImageView img = quizStadium.findViewById(R.id.imageStadio);
                    Picasso.get().load(url).into(img);
                    Button btnA=quizStadium.findViewById(R.id.btn1);
                    Button btnB=quizStadium.findViewById(R.id.btn2);
                    Button btnC=quizStadium.findViewById(R.id.btn3);
                    Button btnD=quizStadium.findViewById(R.id.btn4);
                    btnA.setText(domande.get(i).getOption1());
                    btnB.setText(domande.get(i).getOption2());
                    btnC.setText(domande.get(i).getOption3());
                    btnD.setText(domande.get(i).getOption4());
                    return;
                }
            }
        });
    }
    public void check(){
        DatabaseReference gameStadiumRef = ref.child("GameStadium").child(indexLobby).child("utenti");
        Log.v("1","1");
        gameStadiumRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.child("activePlayer").getValue().equals(false)){
                        Button btn1=quizStadium.findViewById(R.id.btn1);
                        btn1.setEnabled(false);
                        Button btn2=quizStadium.findViewById(R.id.btn2);
                        btn2.setEnabled(false);
                        Button btn3=quizStadium.findViewById(R.id.btn3);
                        btn3.setEnabled(false);
                        Button btn4=quizStadium.findViewById(R.id.btn4);
                        btn4.setEnabled(false);
                        ImageButton indizio1=quizStadium.findViewById(R.id.btnIndizio1);
                        indizio1.setEnabled(false);
                        ImageButton indizio2=quizStadium.findViewById(R.id.btnIndizio2);
                        indizio2.setEnabled(false);
                        ImageButton indizio3=quizStadium.findViewById(R.id.btnIndizio3);
                        indizio3.setEnabled(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
