package com.example.calcioconlaf;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.ContactsContract;
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
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PartitaThread extends Thread{
    QuizStadium quizStadium;
    String indexLobby;
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    ArrayList<PlayerGame> utenti=new ArrayList<>();
    int indice=0;
    int a;
    String username;
    Button btnA;
    Button btnB;
    Button btnC;
    Button btnD;
    TextView punt1;
    TextView punt2;
    TextView punt3;
    TextView punt4;
    String punti="";
    int puntClassifica;
    ArrayList<Quiz> domande=new ArrayList<>();
    ArrayList<TextView> textViewList = new ArrayList<>();
    ArrayList<ImageView> imageViewList = new ArrayList<>();
    int i=0;
    String opzione="";
    int numeroGiocatori;
    Timer timer=new Timer();
    Timer timer2=new Timer();
    boolean indovinato=false;
    public PartitaThread(QuizStadium quizStadium, String indexLobby, String username, ArrayList<Quiz> domande,int numeroGiocatori){
        this.quizStadium=quizStadium;
        this.indexLobby=indexLobby;
        this.username=username;
        this.domande=domande;
        this.numeroGiocatori=numeroGiocatori;

    }

    @Override
    public void run() {
        super.run();
        setGame();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkColor();
                checkPunteggio();
                checkUtenteAttivo();
            }
        },0,100);
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                checkNewGame();
            }
        },0,3000);
    }
    public void setGame(){
        if(i>9){
            quizStadium.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    finishGame();
                }
            });
        }else {
            quizStadium.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String url = domande.get(i).getUrlImage();
                    ImageView img = quizStadium.findViewById(R.id.imageStadio);
                    Picasso.get().load(url).into(img);
                    btnA = quizStadium.findViewById(R.id.btn1);
                    btnB = quizStadium.findViewById(R.id.btn2);
                    btnC = quizStadium.findViewById(R.id.btn3);
                    btnD = quizStadium.findViewById(R.id.btn4);
                    btnA.setBackgroundColor(Color.MAGENTA);
                    btnB.setBackgroundColor(Color.MAGENTA);
                    btnC.setBackgroundColor(Color.MAGENTA);
                    btnD.setBackgroundColor(Color.MAGENTA);

                    btnA.setText(domande.get(i).getOption1());
                    btnB.setText(domande.get(i).getOption2());
                    btnC.setText(domande.get(i).getOption3());
                    btnD.setText(domande.get(i).getOption4());


                    punt1=quizStadium.findViewById(R.id.txtAvatar1);
                    punt2=quizStadium.findViewById(R.id.txtAvatar2);
                    punt3=quizStadium.findViewById(R.id.txtAvatar3);
                    punt4=quizStadium.findViewById(R.id.txtAvatar4);

                    i++;



                    btnA.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            opzione = (String) btnA.getText();
                            utenti.get(indice).setRispostaSel(opzione);
                            int bottone=0;
                            checkOption(opzione,bottone);
                            btnA.setEnabled(false);

                        }
                    });
                    btnB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            opzione = (String) btnB.getText();
                            utenti.get(indice).setRispostaSel(opzione);
                            int bottone=1;
                            checkOption(opzione,bottone);
                            btnB.setEnabled(false);

                        }
                    });
                    btnC.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            opzione = (String) btnC.getText();
                            utenti.get(indice).setRispostaSel(opzione);
                            int bottone=2;
                            checkOption(opzione,bottone);
                            btnC.setEnabled(false);

                        }
                    });
                    btnD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            opzione = (String) btnD.getText();
                            utenti.get(indice).setRispostaSel(opzione);
                            int bottone=3;
                            checkOption(opzione,bottone);
                            btnD.setEnabled(false);
                        }
                    });
                }
            });
        }
    }
    public void checkUtenteAttivo(){
        DatabaseReference gameStadiumRef = ref.child("GameStadium").child(indexLobby).child("utenti");
        gameStadiumRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int contatore = 0;

                textViewList.clear();
                textViewList.add(quizStadium.findViewById(R.id.txtUser1));
                textViewList.add(quizStadium.findViewById(R.id.txtUser2));
                textViewList.add(quizStadium.findViewById(R.id.txtUser3));
                textViewList.add(quizStadium.findViewById(R.id.txtUser4));

                ArrayList<ImageView> imageViewList=new ArrayList<>();
                imageViewList.add(quizStadium.findViewById(R.id.imgAvatar1));
                imageViewList.add(quizStadium.findViewById(R.id.imgAvatar2));
                imageViewList.add(quizStadium.findViewById(R.id.imgAvatar3));
                imageViewList.add(quizStadium.findViewById(R.id.imgAvatar4));

                for(DataSnapshot ds:snapshot.getChildren()) {
                    //Log.v("score",ds.child("score").getValue().toString()+"a");
                    //int score= ((Long) ds.child("score").getValue()).intValue();
                    boolean aiuto4= (boolean) ds.child("aiuto4").getValue();
                    boolean attivo= (boolean) ds.child("activePlayer").getValue();
                    boolean aiuto1= (boolean) ds.child("aiuto1").getValue();
                    boolean aiuto2= (boolean) ds.child("aiuto2").getValue();
                    boolean aiuto3= (boolean) ds.child("aiuto3").getValue();
                    String usernameDb= (String) ds.child("username").getValue();
                    String rispostaSel= (String) ds.child("rispostaSel").getValue();
                    utenti.add(new PlayerGame(username,aiuto1,aiuto2,aiuto3,aiuto4,0,attivo,rispostaSel));
                    Boolean giocatoreAttivo = (Boolean) ds.child("activePlayer").getValue();
                    if(giocatoreAttivo) {
                        indice=contatore;
                        textViewList.get(contatore).setBackgroundColor(Color.YELLOW);
                        imageViewList.get(contatore).setBackgroundColor(Color.YELLOW);
                        Log.v("prova", String.valueOf(textViewList.get(contatore).getText()));
                        if(textViewList.get(contatore).getText().equals(username)){
                            //Button btn1 = quizStadium.findViewById(R.id.btn1);
                            btnA.setEnabled(true);
                            //Button btn2 = quizStadium.findViewById(R.id.btn2);
                            btnB.setEnabled(true);
                            //Button btn3 = quizStadium.findViewById(R.id.btn3);
                            btnC.setEnabled(true);
                            //Button btn4 = quizStadium.findViewById(R.id.btn4);
                            btnD.setEnabled(true);
                            ImageButton indizio1 = quizStadium.findViewById(R.id.btnIndizio1);
                            indizio1.setEnabled(true);
                            ImageButton indizio2 = quizStadium.findViewById(R.id.btnIndizio2);
                            indizio2.setEnabled(true);
                            ImageButton indizio3 = quizStadium.findViewById(R.id.btnIndizio3);
                            indizio3.setEnabled(true);
                        }
                        contatore++;
                    }else{
                        textViewList.get(contatore).setBackgroundColor(Color.WHITE);
                        imageViewList.get(contatore).setBackgroundColor(Color.WHITE);
                        if(textViewList.get(contatore).getText().equals(username)){
                            //Button btn1 = quizStadium.findViewById(R.id.btn1);
                            btnA.setEnabled(false);
                            //Button btn2 = quizStadium.findViewById(R.id.btn2);
                            btnB.setEnabled(false);
                            //Button btn3 = quizStadium.findViewById(R.id.btn3);
                            btnC.setEnabled(false);
                            //Button btn4 = quizStadium.findViewById(R.id.btn4);
                            btnD.setEnabled(false);
                            ImageButton indizio1 = quizStadium.findViewById(R.id.btnIndizio1);
                            indizio1.setEnabled(false);
                            ImageButton indizio2 = quizStadium.findViewById(R.id.btnIndizio2);
                            indizio2.setEnabled(false);
                            ImageButton indizio3 = quizStadium.findViewById(R.id.btnIndizio3);
                            indizio3.setEnabled(false);
                        }
                        contatore++;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public boolean checkOption(String opzione,int bottone){
        String risposta = domande.get(i-1).getAnswer();

        ArrayList<Button> buttonList = new ArrayList<>();
        buttonList.add(quizStadium.findViewById(R.id.btn1));
        buttonList.add(quizStadium.findViewById(R.id.btn2));
        buttonList.add(quizStadium.findViewById(R.id.btn3));
        buttonList.add(quizStadium.findViewById(R.id.btn4));

        textViewList.clear();
        textViewList.add(quizStadium.findViewById(R.id.txtUser1));
        textViewList.add(quizStadium.findViewById(R.id.txtUser2));
        textViewList.add(quizStadium.findViewById(R.id.txtUser3));
        textViewList.add(quizStadium.findViewById(R.id.txtUser4));


        if (!opzione.equals("")) {
            if(opzione.equals(risposta)){
                DatabaseReference bottoneRef = ref.child("GameStadium").child(indexLobby).child("game").child(String.valueOf(bottone));
                bottoneRef.setValue(true);
                DatabaseReference scoreRef = ref.child("GameStadium").child(indexLobby).child("utenti");
                for(int a=0;a<textViewList.size();a++){
                    if(textViewList.get(a).getText().equals(username)){
                        if(a==0) {
                            String ResId = "txtAvatar"+1;
                            int id = quizStadium.getResources().getIdentifier(ResId, "id", quizStadium.getPackageName());
                            TextView txtPunteggio = quizStadium.findViewById(id);
                            int punteggio=Integer.parseInt((String)txtPunteggio.getText());
                            punteggio++;
                            txtPunteggio.setText(String.valueOf(punteggio));
                            scoreRef.child("0").child("score").setValue(String.valueOf(punteggio));
                        }else if(a==1) {
                            String ResId = "txtAvatar"+2;
                            int id = quizStadium.getResources().getIdentifier(ResId, "id", quizStadium.getPackageName());
                            TextView txtPunteggio = quizStadium.findViewById(id);
                            int punteggio=Integer.parseInt((String)txtPunteggio.getText());
                            punteggio++;
                            txtPunteggio.setText(String.valueOf(punteggio));
                            scoreRef.child("1").child("score").setValue(String.valueOf(punteggio));
                        }else if(a==2) {
                            String ResId = "txtAvatar"+3;
                            int id = quizStadium.getResources().getIdentifier(ResId, "id", quizStadium.getPackageName());
                            TextView txtPunteggio = quizStadium.findViewById(id);
                            int punteggio=Integer.parseInt((String)txtPunteggio.getText());
                            punteggio++;
                            txtPunteggio.setText(String.valueOf(punteggio));
                            scoreRef.child("2").child("score").setValue(String.valueOf(punteggio));
                        }else{
                            String ResId = "txtAvatar"+4;
                            int id = quizStadium.getResources().getIdentifier(ResId, "id", quizStadium.getPackageName());
                            TextView txtPunteggio = quizStadium.findViewById(id);
                            int punteggio=Integer.parseInt((String)txtPunteggio.getText());
                            punteggio++;
                            txtPunteggio.setText(String.valueOf(punteggio));
                            scoreRef.child("3").child("score").setValue(String.valueOf(punteggio));
                        }
                    }
                }
            }else{
                DatabaseReference bottoneRef = ref.child("GameStadium").child(indexLobby).child("game").child(String.valueOf(bottone));
                bottoneRef.setValue(false);
                DatabaseReference activePlayerRef=ref.child("GameStadium").child(indexLobby).child("utenti");

                Log.v("numGioc", String.valueOf(numeroGiocatori));
                for(int c=0;c<numeroGiocatori;c++){
                    Log.v("c1",String.valueOf(c));
                    if(textViewList.get(c).getText().equals(username)) {
                        if (c == numeroGiocatori - 1) {
                            activePlayerRef.child(String.valueOf(c)).child("activePlayer").setValue(false);
                            activePlayerRef.child(String.valueOf(0)).child("activePlayer").setValue(true);
                        } else {
                            activePlayerRef.child(String.valueOf(c)).child("activePlayer").setValue(false);
                            activePlayerRef.child(String.valueOf(c + 1)).child("activePlayer").setValue(true);
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }
    public void checkColor() {
        DatabaseReference bottoneRef = ref.child("GameStadium").child(indexLobby).child("game");
        bottoneRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(!ds.getValue().equals("null")) {
                        Boolean settato = (Boolean) ds.getValue();
                        if (settato) {
                            int valore= Integer.parseInt(ds.getKey());
                            switch (valore) {
                                case 0:
                                    btnA.setBackgroundColor(Color.GREEN);
                                    indovinato=true;
                                    break;
                                case 1:
                                    btnB.setBackgroundColor(Color.GREEN);
                                    indovinato=true;
                                    break;
                                case 2:
                                    btnC.setBackgroundColor(Color.GREEN);
                                    indovinato=true;
                                    break;
                                case 3:
                                    btnD.setBackgroundColor(Color.GREEN);
                                    indovinato=true;
                                    break;
                            }
                        } else {
                            int valore= Integer.parseInt(ds.getKey());
                            switch (valore) {
                                case 0:
                                    btnA.setBackgroundColor(Color.RED);
                                    break;
                                case 1:
                                    btnB.setBackgroundColor(Color.RED);
                                    break;
                                case 2:
                                    btnC.setBackgroundColor(Color.RED);
                                    break;
                                case 3:
                                    btnD.setBackgroundColor(Color.RED);
                                    break;
                            }
                        }
                    }else{
                        int valore= Integer.parseInt(ds.getKey());
                        switch (valore) {
                            case 0:
                                btnA.setBackgroundColor(Color.MAGENTA);
                                break;
                            case 1:
                                btnB.setBackgroundColor(Color.MAGENTA);
                                break;
                            case 2:
                                btnC.setBackgroundColor(Color.MAGENTA);
                                break;
                            case 3:
                                btnD.setBackgroundColor(Color.MAGENTA);
                                break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void checkPunteggio(){
        DatabaseReference scoreRef = ref.child("GameStadium").child(indexLobby).child("utenti");
        scoreRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    int utente=Integer.parseInt(ds.getKey());
                    switch(utente){
                        case 0:
                            punti= String.valueOf(ds.child("score").getValue());
                            punt1.setText(punti);
                            break;
                        case 1:
                            punti= String.valueOf(ds.child("score").getValue());
                            punt2.setText(punti);
                            break;
                        case 2:
                            punti= String.valueOf(ds.child("score").getValue());
                            punt3.setText(punti);
                            break;
                        case 3:
                            punti= String.valueOf(ds.child("score").getValue());
                            punt4.setText(punti);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void settaBottoni(){
        DatabaseReference bottoneRef = ref.child("GameStadium").child(indexLobby).child("game");
        bottoneRef.child("0").setValue("null");
        bottoneRef.child("1").setValue("null");
        bottoneRef.child("2").setValue("null");
        bottoneRef.child("3").setValue("null");
    }
    public void newGame(){
        settaBottoni();
        setGame();
        indovinato=false;
    }
    public void checkNewGame(){
        if(indovinato){
            newGame();
        }
        /*DatabaseReference bottoneRef = ref.child("GameStadium").child(indexLobby).child("game");
        bottoneRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if((Boolean)snapshot.child("4").getValue()){
                    newGame();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }
    public void finishGame(){
        timer.cancel();
        btnA.setEnabled(false);
        btnB.setEnabled(false);
        btnC.setEnabled(false);
        btnD.setEnabled(false);
        ImageButton indizio1 = quizStadium.findViewById(R.id.btnIndizio1);
        indizio1.setEnabled(false);
        ImageButton indizio2 = quizStadium.findViewById(R.id.btnIndizio2);
        indizio2.setEnabled(false);
        ImageButton indizio3 = quizStadium.findViewById(R.id.btnIndizio3);
        indizio3.setEnabled(false);
        DatabaseReference utentiRef = ref.child("GameStadium").child(indexLobby).child("utenti");
        ArrayList<Integer> punteggi=new ArrayList<Integer>();
        ArrayList<String> nomiUtente=new ArrayList<String>();
        utentiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int punteggioMax=0;
                String usernameVincitore="";
                for(DataSnapshot ds:snapshot.getChildren()){
                    int score= Integer.valueOf(String.valueOf(ds.child("score").getValue()));
                    String nome=String.valueOf(ds.child("username").getValue());
                    if(Integer.valueOf(String.valueOf(ds.child("score").getValue()))>punteggioMax){
                        punteggioMax=Integer.valueOf(String.valueOf(ds.child("score").getValue()));
                        usernameVincitore=String.valueOf(ds.child("username").getValue());
                    }
                    punteggi.add(score);
                    nomiUtente.add(nome);
                }
                for(int e=0;e< snapshot.getChildrenCount();e++){
                    Log.v("punteggi", String.valueOf(punteggi.get(e)));
                    Log.v("nomi", String.valueOf(nomiUtente.get(e)));
                }
                if(punteggi.size()==numeroGiocatori){
                    for(a=0;a<punteggi.size();a++){
                        if(nomiUtente.get(a).equals(username)){
                            puntClassifica=punteggi.get(a);
                            if(usernameVincitore.equals(username)){
                                AlertDialog alertDialog;
                                alertDialog=new AlertDialog.Builder(quizStadium).setTitle("Result")
                                        .setMessage(username+" sei il vincitore, il tuo punteggio e' di: "+puntClassifica).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=new Intent(quizStadium,GameActivity.class);
                                        quizStadium.startActivity(intent);
                                    }
                                },2000);
                            }else{
                                AlertDialog alertDialog;
                                alertDialog=new AlertDialog.Builder(quizStadium).setTitle("Result")
                                        .setMessage(username+" non hai vinto, il tuo punteggio è di: "+puntClassifica).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=new Intent(quizStadium,GameActivity.class);
                                        quizStadium.startActivity(intent);
                                    }
                                },2000);
                            }
                        }
                        DatabaseReference classificaRef=ref.child("LeaderBoardStadium").child(username);
                        classificaRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.getChildrenCount()>0){
                                    int puntVecchio=Integer.parseInt(String.valueOf(snapshot.getValue()));
                                    if(puntVecchio<puntClassifica){
                                        classificaRef.setValue(puntClassifica);
                                    }
                                }else{
                                    classificaRef.setValue(puntClassifica);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

