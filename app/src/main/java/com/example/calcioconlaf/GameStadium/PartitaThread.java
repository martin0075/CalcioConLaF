package com.example.calcioconlaf.GameStadium;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.calcioconlaf.GameActivity;
import com.example.calcioconlaf.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    ImageButton help1;
    ImageButton help2;
    ImageButton help3;
    TextView punt1;
    TextView punt2;
    TextView punt3;
    TextView punt4;
    String punti="";
    int puntClassifica;
    ArrayList<Quiz> domande=new ArrayList<>();
    ArrayList<TextView> textViewList = new ArrayList<>();
    ArrayList<ImageView> imageViewList = new ArrayList<>();
    ArrayList<String> sbagliate=new ArrayList<>();
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
                //checkColor();
                checkPunteggio();
                checkUtenteAttivo();
                checkHelp();

            }
        },0,100);
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                checkColor();
                checkNewGame();
            }
        },0,2000);
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


                    punt1 = quizStadium.findViewById(R.id.txtAvatar1);
                    punt2 = quizStadium.findViewById(R.id.txtAvatar2);
                    punt3 = quizStadium.findViewById(R.id.txtAvatar3);
                    punt4 = quizStadium.findViewById(R.id.txtAvatar4);



                    btnA.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            opzione = (String) btnA.getText();
                            utenti.get(indice).setRispostaSel(opzione);
                            int bottone = 0;
                            checkOption(opzione, bottone);
                            btnA.setEnabled(false);

                        }
                    });
                    btnB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            opzione = (String) btnB.getText();
                            utenti.get(indice).setRispostaSel(opzione);
                            int bottone = 1;
                            checkOption(opzione, bottone);
                            btnB.setEnabled(false);

                        }
                    });
                    btnC.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            opzione = (String) btnC.getText();
                            utenti.get(indice).setRispostaSel(opzione);
                            int bottone = 2;
                            checkOption(opzione, bottone);
                            btnC.setEnabled(false);

                        }
                    });
                    btnD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            opzione = (String) btnD.getText();
                            utenti.get(indice).setRispostaSel(opzione);
                            int bottone = 3;
                            checkOption(opzione, bottone);
                            btnD.setEnabled(false);
                        }
                    });

                    help1 = quizStadium.findViewById(R.id.btnIndizio1);
                    help2 = quizStadium.findViewById(R.id.btnIndizio2);
                    help3 = quizStadium.findViewById(R.id.btnIndizio3);
                    help1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String citta = domande.get(i-1).getCity();
                            Toast.makeText(quizStadium, "Città: " + citta, Toast.LENGTH_LONG).show();
                            DatabaseReference helpRef = ref.child("GameStadium").child(indexLobby).child("utenti");
                            for (int a = 0; a < textViewList.size(); a++) {
                                if (textViewList.get(a).getText().equals(username)) {
                                    helpRef.child(String.valueOf(a)).child(("aiuto1")).setValue(true);
                                    help1.setClickable(false);
                                    help1.setEnabled(false);
                                    help1.setBackgroundColor(Color.RED);
                                }
                            }
                        }
                    });
                    help2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String nazione = domande.get(i-1).getCountry();
                            Toast.makeText(quizStadium, "Nazione: " + nazione, Toast.LENGTH_LONG).show();
                            DatabaseReference helpRef = ref.child("GameStadium").child(indexLobby).child("utenti");
                            for (int b = 0; b < textViewList.size(); b++) {
                                if (textViewList.get(b).getText().equals(username)) {
                                    helpRef.child(String.valueOf(b)).child(("aiuto2")).setValue(true);
                                    help2.setClickable(false);
                                    help2.setEnabled(false);
                                    help2.setBackgroundColor(Color.RED);
                                }
                            }

                        }
                    });
                    ArrayList<String> opt = new ArrayList<>();
                    opt.add(domande.get(i).getOption1());
                    opt.add(domande.get(i).getOption2());
                    opt.add(domande.get(i).getOption3());
                    opt.add(domande.get(i).getOption4());
                    help3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Toast.makeText(quizStadium, "1 opzione eliminata", Toast.LENGTH_LONG).show();
                            DatabaseReference helpRef = ref.child("GameStadium").child(indexLobby).child("utenti");
                            boolean trovato = false;
                            while (!trovato) {
                                Random random = new Random();
                                int dim = opt.size();
                                int elimina = random.nextInt(dim);
                                switch (elimina) {
                                    case 0:
                                        String opt1= String.valueOf(btnA.getText());
                                        Log.v("opt",opt1);
                                            if(!sbagliate.contains(opt1)) {
                                                Log.v("sbagliate","sbagliate");
                                                if (!opt1.equals(domande.get(i - 1).getAnswer())) {
                                                    Log.v("risposta","risposta");
                                                    DatabaseReference game = ref.child("GameStadium").child(indexLobby).child("game");
                                                    game.child(String.valueOf(elimina)).setValue(false);
                                                    for (int c = 0; c < textViewList.size(); c++) {
                                                        if (textViewList.get(c).getText().equals(username)) {
                                                            Log.v("ifuser","ifuser");
                                                            helpRef.child(String.valueOf(c)).child(("aiuto3")).setValue(true);
                                                            help3.setClickable(false);
                                                            help3.setEnabled(false);
                                                            help3.setBackgroundColor(Color.RED);
                                                            trovato = true;
                                                            c=textViewList.size()-1;
                                                        }
                                                    }
                                                }else{
                                                    trovato=false;
                                                    Log.v("elserisposta","elserisposta");
                                                }
                                        }else{
                                                trovato=false;
                                                Log.v("elsesbagliate","elsesbagliate");
                                            }
                                        break;
                                    case 1:
                                        String opt2= String.valueOf(btnB.getText());
                                        Log.v("opt",opt2);
                                        if(!sbagliate.contains(opt2)) {
                                            Log.v("sbagliate","sbagliate");
                                            if (!opt2.equals(domande.get(i - 1).getAnswer())) {
                                                Log.v("risposta","risposta");
                                                DatabaseReference game = ref.child("GameStadium").child(indexLobby).child("game");
                                                game.child(String.valueOf(elimina)).setValue(false);
                                                for (int c = 0; c < textViewList.size(); c++) {
                                                    if (textViewList.get(c).getText().equals(username)) {
                                                        Log.v("ifuser","ifuser");
                                                        helpRef.child(String.valueOf(c)).child(("aiuto3")).setValue(true);
                                                        help3.setClickable(false);
                                                        help3.setEnabled(false);
                                                        Boolean prova=help3.isEnabled();
                                                        Boolean prova2=help3.isClickable();
                                                        help3.setBackgroundColor(Color.RED);
                                                        trovato = true;
                                                        c=textViewList.size()-1;
                                                    }
                                                }
                                            }else{
                                                trovato=false;
                                                Log.v("elserisposta","elserisposta");
                                            }
                                        }else {
                                            trovato = false;
                                            Log.v("elsesbagliate","elsesbagliate");
                                        }
                                        break;
                                    case 2:
                                        String opt3= String.valueOf(btnC.getText());
                                        Log.v("opt",opt3);
                                            if (!sbagliate.contains(opt3)) {
                                                Log.v("sbagliate","sbagliate");
                                                if (!domande.get(i - 1).getOption3().equals(domande.get(i - 1).getAnswer())) {
                                                    Log.v("risposta","risposta");

                                                    DatabaseReference game = ref.child("GameStadium").child(indexLobby).child("game");
                                                    game.child(String.valueOf(elimina)).setValue(false);
                                                    for (int c = 0; c < textViewList.size(); c++) {
                                                        if (textViewList.get(c).getText().equals(username)) {
                                                            Log.v("ifuser","ifuser");
                                                            helpRef.child(String.valueOf(c)).child(("aiuto3")).setValue(true);
                                                            help3.setClickable(false);
                                                            help3.setEnabled(false);
                                                            Boolean prova=help3.isEnabled();
                                                            Boolean prova2=help3.isClickable();
                                                            help3.setBackgroundColor(Color.RED);
                                                            trovato=true;
                                                            c=textViewList.size()-1;
                                                        }
                                                    }
                                                }else{
                                                    trovato=false;
                                                    Log.v("elserisposta","elserisposta");
                                                }
                                            }else{
                                                trovato=false;
                                                Log.v("elsesbagliate","elsesbagliate");
                                            }
                                        break;
                                    case 3:
                                        String opt4= String.valueOf(btnD.getText());
                                        Log.v("opt4",opt4);
                                            if(!sbagliate.contains(opt4)) {
                                                Log.v("sbagliate","sbagliate");
                                                if (!opt4.equals(domande.get(i - 1).getAnswer())) {
                                                    Log.v("risposta","risposta");
                                                    DatabaseReference game = ref.child("GameStadium").child(indexLobby).child("game");
                                                    game.child(String.valueOf(elimina)).setValue(false);
                                                    for (int c = 0; c < textViewList.size(); c++) {
                                                        if (textViewList.get(c).getText().equals(username)) {
                                                            Log.v("ifuser","ifuser");
                                                            helpRef.child(String.valueOf(c)).child(("aiuto3")).setValue(true);
                                                            help3.setClickable(false);
                                                            help3.setEnabled(false);
                                                            Boolean prova=help3.isEnabled();
                                                            Boolean prova2=help3.isClickable();
                                                            help3.setBackgroundColor(Color.RED);
                                                            trovato = true;
                                                            c=textViewList.size()-1;
                                                        }
                                                    }
                                                }else{
                                                    trovato=false;
                                                    Log.v("elserisposta","elserisposta");
                                                }
                                            }else {
                                                trovato = false;
                                                Log.v("elsesbagliate","elsesbagliate");
                                            }
                                        break;
                                }
                            }
                        }
                    });
                    i++;
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
                        if(textViewList.get(contatore).getText().equals(username)){
                            btnA.setEnabled(true);
                            btnB.setEnabled(true);
                            btnC.setEnabled(true);
                            btnD.setEnabled(true);
                            help1.setEnabled(true);
                            help1.setClickable(true);
                            help2.setEnabled(true);
                            help2.setClickable(true);
                            help3.setEnabled(true);
                            help3.setClickable(true);


                        }
                        contatore++;
                    }else{
                        textViewList.get(contatore).setBackgroundColor(Color.WHITE);
                        imageViewList.get(contatore).setBackgroundColor(Color.WHITE);
                        if(textViewList.get(contatore).getText().equals(username)){
                            btnA.setEnabled(false);
                            btnB.setEnabled(false);
                            btnC.setEnabled(false);
                            btnD.setEnabled(false);
                            help1.setEnabled(false);
                            help1.setClickable(false);
                            help2.setEnabled(false);
                            help2.setClickable(false);
                            help3.setEnabled(false);
                            help3.setClickable(false);
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

                for(int c=0;c<numeroGiocatori;c++){
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
                                    if(!sbagliate.contains(String.valueOf(btnA.getText()))){
                                        sbagliate.add(String.valueOf(btnA.getText()));
                                    }
                                    break;
                                case 1:
                                    btnB.setBackgroundColor(Color.RED);
                                    if(!sbagliate.contains(String.valueOf(btnB.getText()))){
                                        sbagliate.add(String.valueOf(btnB.getText()));
                                    }

                                    break;
                                case 2:
                                    btnC.setBackgroundColor(Color.RED);
                                    if(!sbagliate.contains(String.valueOf(btnC.getText()))){
                                        sbagliate.add(String.valueOf(btnC.getText()));
                                    }
                                    break;
                                case 3:
                                    btnD.setBackgroundColor(Color.RED);
                                    if(!sbagliate.contains(String.valueOf(btnD.getText()))){
                                        sbagliate.add(String.valueOf(btnD.getText()));
                                    }

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
        sbagliate.clear();
        settaBottoni();
        setGame();
    }
    public void checkNewGame(){
        if(indovinato){
            indovinato=false;
            newGame();
        }
    }
    public void checkHelp(){
        DatabaseReference helpRef = ref.child("GameStadium").child(indexLobby).child("utenti");
        helpRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    if(ds.child("username").getValue().equals(username)){
                        Boolean aiuto1 = (Boolean) ds.child("aiuto1").getValue();
                        Boolean aiuto2=(Boolean) ds.child("aiuto2").getValue();
                        Boolean aiuto3=(Boolean) ds.child("aiuto3").getValue();
                        if(aiuto1){
                            help1.setClickable(false);
                            help1.setEnabled(false);
                            help1.setBackgroundColor(Color.RED);
                        }/*else {
                            help1.setClickable(false);
                            help1.setEnabled(false);
                            help1.setBackgroundColor(Color.GREEN);

                        }*/
                        if(aiuto2) {
                            help2.setClickable(false);
                            help2.setEnabled(false);
                            help2.setBackgroundColor(Color.RED);
                        }
                        if(aiuto3){
                            help3.setClickable(false);
                            help3.setEnabled(false);
                            help3.setBackgroundColor(Color.RED);
                        }
                    }
                    /*if(aiuto1){
                        for(int a=0;a<textViewList.size();a++) {
                            if (textViewList.get(a).getText().equals(username)) {
                                help1.setClickable(false);
                                help1.setEnabled(false);
                                help1.setBackgroundColor(Color.RED);
                            }
                        }*/
                    /*}else{
                        for(int b=0;b<textViewList.size();b++){
                            if(textViewList.get(b).getText().equals(username)){
                                help1.setBackgroundColor(Color.TRANSPARENT);
                            }
                        }
                    }
                    if(aiuto2){
                        for(int c=0;c<textViewList.size();c++) {
                            if (textViewList.get(c).getText().equals(username)) {
                                help2.setClickable(false);
                                help2.setEnabled(false);
                                help2.setBackgroundColor(Color.RED);
                            }
                        }
                    }else{
                        for(int d=0;d<textViewList.size();d++) {
                            if (textViewList.get(d).getText().equals(username)) {
                                help2.setBackgroundColor(Color.TRANSPARENT);
                            }
                        }
                    }*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                if(punteggi.size()==numeroGiocatori){
                    Boolean pareggio=false;
                    int contaPari=0;
                    for(int f=0;f<punteggi.size();f++){
                        if(punteggi.get(f)!=contaPari){
                            contaPari=punteggi.get(f);
                        }else{
                            pareggio=true;
                        }
                    }
                    if(pareggio){
                        for (a = 0; a < punteggi.size(); a++) {
                            if (nomiUtente.get(a).equals(username)) {
                                puntClassifica = punteggi.get(a);
                            }
                        }
                        AlertDialog alertDialog;
                        alertDialog=new AlertDialog.Builder(quizStadium).setTitle("Result")
                                .setMessage("La partita e' finita in pareggio, il tuo punteggio e' di: "+contaPari).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent=new Intent(quizStadium, GameActivity.class);
                                intent.putExtra("Username", username);
                                quizStadium.startActivity(intent);
                                ref.child("GameStadium").child(indexLobby).setValue(null);
                            }
                        },2000);
                        DatabaseReference classificaRef = ref.child("LeaderBoardStadium").child(username);
                        classificaRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    int puntVecchio = Integer.parseInt(String.valueOf(snapshot.getValue()));
                                    puntVecchio=puntVecchio+puntClassifica;
                                    classificaRef.setValue(puntVecchio);
                                    Log.v("puntVecchio", String.valueOf(puntVecchio));
                                    /*if (puntVecchio < puntClassifica) {
                                        classificaRef.setValue(puntClassifica);
                                    }else{
                                        classificaRef.setValue(puntVecchio);
                                    }*/
                                }else{
                                    classificaRef.setValue(puntClassifica);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else {
                        for (a = 0; a < punteggi.size(); a++) {
                            if (nomiUtente.get(a).equals(username)) {
                                puntClassifica = punteggi.get(a);
                                if (usernameVincitore.equals(username)) {
                                    AlertDialog alertDialog;
                                    alertDialog = new AlertDialog.Builder(quizStadium).setTitle("Result")
                                            .setMessage(username + " sei il vincitore, il tuo punteggio e' di: " + puntClassifica).show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(quizStadium, GameActivity.class);
                                            quizStadium.startActivity(intent);
                                            ref.child("GameStadium").child(indexLobby).setValue(null);
                                        }
                                    }, 2000);
                                } else {
                                    AlertDialog alertDialog;
                                    alertDialog = new AlertDialog.Builder(quizStadium).setTitle("Result")
                                            .setMessage(username + " non hai vinto, il tuo punteggio è di: " + puntClassifica).show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(quizStadium, GameActivity.class);
                                            intent.putExtra("Username", username);
                                            quizStadium.startActivity(intent);
                                            ref.child("GameStadium").child(indexLobby).setValue(null);
                                        }
                                    }, 2000);
                                }
                            }
                        }
                        DatabaseReference classificaRef = ref.child("LeaderBoardStadium").child(username);
                        classificaRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    int puntVecchio = Integer.parseInt(String.valueOf(snapshot.getValue()));
                                    puntVecchio=puntVecchio+puntClassifica;
                                    classificaRef.setValue(puntVecchio);
                                    Log.v("puntVecchio", String.valueOf(puntVecchio));
                                        /*if (puntVecchio < puntClassifica) {
                                            classificaRef.setValue(puntClassifica);
                                        }else{
                                            classificaRef.setValue(puntVecchio);
                                        }*/
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

