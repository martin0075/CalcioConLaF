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
    int indice=0;
    String opzione="";
    int i=0;
    ArrayList<PlayerGame> utenti=new ArrayList<>();
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
                checkUtenteAttivo();
                Log.v("checkAttivo2", "checkAttivo2");
                String url = domande.get(i).getUrlImage();
                ImageView img = quizStadium.findViewById(R.id.imageStadio);
                Picasso.get().load(url).into(img);
                btnA = quizStadium.findViewById(R.id.btn1);
                btnB = quizStadium.findViewById(R.id.btn2);
                btnC = quizStadium.findViewById(R.id.btn3);
                btnD = quizStadium.findViewById(R.id.btn4);
                btnA.setText(domande.get(i).getOption1());
                btnB.setText(domande.get(i).getOption2());
                btnC.setText(domande.get(i).getOption3());
                btnD.setText(domande.get(i).getOption4());

                btnA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        opzione = (String) btnA.getText();
                        utenti.get(indice).setRispostaSel(opzione);
                        checkOption(opzione,0);
                    }
                });
                btnB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        opzione = (String) btnB.getText();
                        utenti.get(indice).setRispostaSel(opzione);
                        checkOption(opzione,1);
                    }
                });
                btnC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        opzione = (String) btnC.getText();
                        utenti.get(indice).setRispostaSel(opzione);
                        checkOption(opzione,2);
                    }
                });
                btnD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        opzione = (String) btnD.getText();
                        utenti.get(indice).setRispostaSel(opzione);
                        checkOption(opzione,3);
                    }
                });
            }
        });
    }
    public void checkUtenteAttivo(){
        Log.v("checkAttivo","checkAttivo");
        DatabaseReference gameStadiumRef = ref.child("GameStadium").child(indexLobby).child("utenti");
        gameStadiumRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int contatore = 0;
                int contatoreWhile=0;
                ArrayList<TextView> textViewList = new ArrayList<>();
                textViewList.add(quizStadium.findViewById(R.id.txtUser1));
                textViewList.add(quizStadium.findViewById(R.id.txtUser2));
                textViewList.add(quizStadium.findViewById(R.id.txtUser3));
                textViewList.add(quizStadium.findViewById(R.id.txtUser4));
                ArrayList<ImageView> imageViewList = new ArrayList<>();
                imageViewList.add(quizStadium.findViewById(R.id.imgAvatar1));
                imageViewList.add(quizStadium.findViewById(R.id.imgAvatar2));
                imageViewList.add(quizStadium.findViewById(R.id.imgAvatar3));
                imageViewList.add(quizStadium.findViewById(R.id.imgAvatar4));

                for(DataSnapshot ds:snapshot.getChildren()) {
                    int score= ((Long) ds.child("score").getValue()).intValue();
                    boolean aiuto4= (boolean) ds.child("aiuto4").getValue();
                    boolean attivo= (boolean) ds.child("activePlayer").getValue();
                    boolean aiuto1= (boolean) ds.child("aiuto1").getValue();
                    boolean aiuto2= (boolean) ds.child("aiuto2").getValue();
                    boolean aiuto3= (boolean) ds.child("aiuto3").getValue();
                    String username= (String) ds.child("username").getValue();
                    String rispostaSel= (String) ds.child("rispostaSel").getValue();
                    utenti.add(new PlayerGame(username,aiuto1,aiuto2,aiuto3,aiuto4,score,attivo,rispostaSel));
                    Boolean giocatoreAttivo = (Boolean) ds.child("activePlayer").getValue();
                    if(giocatoreAttivo) {
                        indice=contatore;
                        textViewList.get(contatore).setBackgroundColor(Color.YELLOW);
                        imageViewList.get(contatore).setBackgroundColor(Color.YELLOW);
                    }else{
                        contatore++;
                    }
                }
                while(contatoreWhile<snapshot.getChildrenCount()){
                    Log.v("while", "while");
                    if(snapshot.child(String.valueOf(contatoreWhile)).child("username").getValue().equals(username)) {
                        Boolean activePlayer = (Boolean) snapshot.child(String.valueOf(contatoreWhile)).child("activePlayer").getValue();
                        if (!activePlayer) {
                            Button btn1 = quizStadium.findViewById(R.id.btn1);
                            btn1.setEnabled(false);
                            Button btn2 = quizStadium.findViewById(R.id.btn2);
                            btn2.setEnabled(false);
                            Button btn3 = quizStadium.findViewById(R.id.btn3);
                            btn3.setEnabled(false);
                            Button btn4 = quizStadium.findViewById(R.id.btn4);
                            btn4.setEnabled(false);
                            ImageButton indizio1 = quizStadium.findViewById(R.id.btnIndizio1);
                            indizio1.setEnabled(false);
                            ImageButton indizio2 = quizStadium.findViewById(R.id.btnIndizio2);
                            indizio2.setEnabled(false);
                            ImageButton indizio3 = quizStadium.findViewById(R.id.btnIndizio3);
                            indizio3.setEnabled(false);
                        }
                        contatoreWhile= (int) snapshot.getChildrenCount();
                    }else{
                        contatoreWhile++;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public boolean checkOption(String opzione,int bottone) {
        String risposta = domande.get(i).getAnswer();

        ArrayList<Button> buttonList = new ArrayList<>();
        buttonList.add(quizStadium.findViewById(R.id.btn1));
        buttonList.add(quizStadium.findViewById(R.id.btn2));
        buttonList.add(quizStadium.findViewById(R.id.btn3));
        buttonList.add(quizStadium.findViewById(R.id.btn4));

        ArrayList<TextView> textViewList = new ArrayList<>();
        textViewList.add(quizStadium.findViewById(R.id.txtUser1));
        textViewList.add(quizStadium.findViewById(R.id.txtUser2));
        textViewList.add(quizStadium.findViewById(R.id.txtUser3));
        textViewList.add(quizStadium.findViewById(R.id.txtUser4));
        if (!opzione.equals("")) {
            if(opzione.equals(risposta)){
                buttonList.get(bottone).setBackgroundColor(Color.GREEN);
                for(int d=0;d<buttonList.size();d++){
                    buttonList.get(d).setEnabled(false);
                }
                for(int a=0;a<textViewList.size();a++){
                    if(textViewList.get(a).getText().equals(username)){
                        String ResId = "txtAvatar" + (a + 1);
                        int id = quizStadium.getResources().getIdentifier(ResId, "id", quizStadium.getPackageName());
                        TextView txtPunteggio = quizStadium.findViewById(id);
                        int punteggio = Integer.parseInt((String) txtPunteggio.getText());
                        punteggio++;
                        txtPunteggio.setText(String.valueOf(punteggio));
                    }
                }
            }else{
                buttonList.get(bottone).setBackgroundColor(Color.RED);
                buttonList.get(bottone).setEnabled(false);
                return false;
            }
        }
        return true;
    }
}
