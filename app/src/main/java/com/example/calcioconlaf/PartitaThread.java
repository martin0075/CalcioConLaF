package com.example.calcioconlaf;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PartitaThread extends Thread{
    ArrayList<Quiz> domande=new ArrayList<>();
    QuizStadium quizStadium;
    String indexLobby;
    String username;
    Button btnA;
    Button btnB;
    Button btnC;
    Button btnD;
    int i=0;
    int cont=0;
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();

    public PartitaThread(ArrayList<Quiz> domande,QuizStadium quizStadium,String indexLobby,String username) {
        this.domande=domande;
        this.quizStadium=quizStadium;
        this.indexLobby=indexLobby;
        this.username=username;
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
                    String url = domande.get(i).getUrlImage();
                    ImageView img = quizStadium.findViewById(R.id.imageStadio);
                    Picasso.get().load(url).into(img);
                    btnA=quizStadium.findViewById(R.id.btn1);
                    btnB=quizStadium.findViewById(R.id.btn2);
                    btnC=quizStadium.findViewById(R.id.btn3);
                    btnD=quizStadium.findViewById(R.id.btn4);
                    btnA.setText(domande.get(i).getOption1());
                    btnB.setText(domande.get(i).getOption2());
                    btnC.setText(domande.get(i).getOption3());
                    btnD.setText(domande.get(i).getOption4());

                    btnA.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String opzione= (String) btnA.getText();
                            checkOption(opzione,view);
                        }
                    });
                    btnB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String opzione= (String) btnB.getText();
                            checkOption(opzione,view);
                        }
                    });
                    btnC.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String opzione= (String) btnC.getText();
                            checkOption(opzione,view);
                        }
                    });
                    btnD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String opzione= (String) btnD.getText();
                            checkOption(opzione,view);
                        }
                    });
                }
            }
        });
    }
    public void check(){
        DatabaseReference gameStadiumRef = ref.child("GameStadium").child(indexLobby).child("utenti");
        gameStadiumRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.child("username").equals(username)){
                        Boolean activePlayer= (Boolean) ds.child("activePlayer").getValue();
                        Log.v("activePlayer", String.valueOf(activePlayer));
                        if(!activePlayer){
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public boolean checkOption(String opzione,View view){
        String risposta=domande.get(i).getAnswer();
        if(opzione.equals(risposta)){
            view.setBackgroundColor(Color.GREEN);
            ArrayList<TextView> textViewList=new ArrayList<>();
            textViewList.add(quizStadium.findViewById(R.id.txtUser1));
            textViewList.add(quizStadium.findViewById(R.id.txtUser2));
            textViewList.add(quizStadium.findViewById(R.id.txtUser3));
            textViewList.add(quizStadium.findViewById(R.id.txtUser4));
            for(int a=0;a<textViewList.size();a++){
                Log.v("getA",textViewList.get(a).getText()+"prova");
                int visibility=textViewList.get(a).getVisibility();
                Log.v("visib",String.valueOf(visibility));
                if(visibility==0) {
                    if (textViewList.get(a).getText().equals(username)) {
                        String ResId = "txtAvatar" + (a + 1);
                        int id = quizStadium.getResources().getIdentifier(ResId, "id", quizStadium.getPackageName());
                        TextView txtPunteggio = quizStadium.findViewById(id);
                        int punteggio = Integer.parseInt((String) txtPunteggio.getText());
                        punteggio++;
                        txtPunteggio.setText(String.valueOf(punteggio));
                    }
                }
            }
        }else{
            view.setBackgroundColor(Color.RED);
            return false;
        }
        return true;
    }
}
