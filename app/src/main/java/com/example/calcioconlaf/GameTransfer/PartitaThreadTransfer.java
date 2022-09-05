package com.example.calcioconlaf.GameTransfer;

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

public class PartitaThreadTransfer extends Thread{
    QuizTransferActivity quizTransferActivity;
    String indexLobby;
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    ArrayList<PlayerGameTransfer> utenti=new ArrayList<>();
    int indice=0;
    int a;
    String username;
    Button btnA;
    Button btnB;
    Button btnC;
    Button btnD;
    TextView domanda;
    ImageButton help1;
    ImageButton help2;
    ImageButton help3;
    TextView punt1;
    TextView punt2;
    TextView punt3;
    TextView punt4;
    String punti="";
    int puntClassifica;
    ArrayList<QuizTransfer> domande=new ArrayList<>();
    ArrayList<TextView> textViewList = new ArrayList<>();
    ArrayList<ImageView> imageViewList = new ArrayList<>();
    ArrayList<String> sbagliate=new ArrayList<>();
    int i=0;
    String opzione="";
    int numeroGiocatori;
    Timer timer=new Timer();
    Timer timer2=new Timer();
    boolean indovinato=false;
    public PartitaThreadTransfer(QuizTransferActivity quizTransferActivity, String indexLobby, String username, ArrayList<QuizTransfer> domande,int numeroGiocatori){
        this.quizTransferActivity=quizTransferActivity;
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
                checkHelp();

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
            quizTransferActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    finishGame();
                }
            });
        }else {
            quizTransferActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String url = domande.get(i).getUrlImage();
                    ImageView img = quizTransferActivity.findViewById(R.id.imageStadio);
                    Picasso.get().load(url).into(img);
                    btnA = quizTransferActivity.findViewById(R.id.btn1);
                    btnB = quizTransferActivity.findViewById(R.id.btn2);
                    btnC = quizTransferActivity.findViewById(R.id.btn3);
                    btnD = quizTransferActivity.findViewById(R.id.btn4);
                    btnA.setBackgroundColor(Color.MAGENTA);
                    btnB.setBackgroundColor(Color.MAGENTA);
                    btnC.setBackgroundColor(Color.MAGENTA);
                    btnD.setBackgroundColor(Color.MAGENTA);

                    btnA.setText(domande.get(i).getOption1());
                    btnB.setText(domande.get(i).getOption2());
                    btnC.setText(domande.get(i).getOption3());
                    btnD.setText(domande.get(i).getOption4());

                    domanda=quizTransferActivity.findViewById(R.id.txtDomanda);
                    domanda.setText(domande.get(i).getDomanda());
                    punt1 = quizTransferActivity.findViewById(R.id.txtAvatar1);
                    punt2 = quizTransferActivity.findViewById(R.id.txtAvatar2);
                    punt3 = quizTransferActivity.findViewById(R.id.txtAvatar3);
                    punt4 = quizTransferActivity.findViewById(R.id.txtAvatar4);



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

                    help1 = quizTransferActivity.findViewById(R.id.btnIndizio1);
                    help2 = quizTransferActivity.findViewById(R.id.btnIndizio2);
                    help3 = quizTransferActivity.findViewById(R.id.btnIndizio3);
                    help1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String citta = domande.get(i-1).getCity();
                            Toast.makeText(quizTransferActivity, "Città: " + citta, Toast.LENGTH_LONG).show();
                            DatabaseReference helpRef = ref.child("GameTransfer").child(indexLobby).child("utenti");
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
                            Toast.makeText(quizTransferActivity, "Nazione: " + nazione, Toast.LENGTH_LONG).show();
                            DatabaseReference helpRef = ref.child("GameTransfer").child(indexLobby).child("utenti");
                            for (int b = 0; b < textViewList.size(); b++) {
                                if (textViewList.get(b).getText().equals(username)) {
                                    helpRef.child(String.valueOf(b)).child(("aiuto2")).setValue(true);
                                    help2.setClickable(false);
                                    help2.setEnabled(false);
                                    view.setBackgroundColor(Color.RED);
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

                            Toast.makeText(quizTransferActivity, "1 opzione eliminata", Toast.LENGTH_LONG).show();
                            DatabaseReference helpRef = ref.child("GameTransfer").child(indexLobby).child("utenti");
                            boolean trovato = false;
                            while (!trovato) {
                                Random random = new Random();
                                int dim = opt.size();
                                int elimina = random.nextInt(dim);
                                switch (elimina) {
                                    case 0:
                                        String opt1= String.valueOf(btnA.getText());
                                        if(!sbagliate.contains(opt1)) {
                                            if (!opt1.equals(domande.get(i - 1).getAnswer())) {
                                                Log.v("domande1", domande.get(i - 1).getOption1());
                                                Log.v("domande2", domande.get(i - 1).getAnswer());
                                                Log.v("elimina1", String.valueOf(elimina));
                                                DatabaseReference game = ref.child("GameTransfer").child(indexLobby).child("game");
                                                game.child(String.valueOf(elimina)).setValue(false);
                                                for (int c = 0; c < textViewList.size(); c++) {
                                                    if (textViewList.get(c).getText().equals(username)) {
                                                        helpRef.child(String.valueOf(c)).child(("aiuto3")).setValue(true);
                                                        help3.setClickable(false);
                                                        help3.setEnabled(false);
                                                        help3.setBackgroundColor(Color.RED);
                                                    }
                                                }
                                            }
                                            trovato = true;
                                        }else{
                                            trovato=false;
                                        }
                                        break;
                                    case 1:
                                        String opt2= String.valueOf(btnB.getText());
                                        if(!sbagliate.contains(opt2)) {
                                            if (!opt2.equals(domande.get(i - 1).getAnswer())) {
                                                Log.v("domande1", domande.get(i - 1).getOption2());
                                                Log.v("domande2", domande.get(i - 1).getAnswer());
                                                Log.v("elimina2", String.valueOf(elimina));
                                                DatabaseReference game = ref.child("GameTransfer").child(indexLobby).child("game");
                                                game.child(String.valueOf(elimina)).setValue(false);
                                                for (int c = 0; c < textViewList.size(); c++) {
                                                    if (textViewList.get(c).getText().equals(username)) {
                                                        helpRef.child(String.valueOf(c)).child(("aiuto3")).setValue(true);
                                                        help3.setClickable(false);
                                                        help3.setEnabled(false);
                                                        help3.setBackgroundColor(Color.RED);
                                                    }
                                                }
                                            }
                                            trovato = true;
                                        }else {
                                            trovato = false;
                                        }
                                        break;
                                    case 2:
                                        String opt3= String.valueOf(btnC.getText());
                                        if (!sbagliate.contains(opt3)) {
                                            if (!domande.get(i - 1).getOption3().equals(domande.get(i - 1).getAnswer())) {
                                                Log.v("domande1", domande.get(i - 1).getOption3());
                                                Log.v("domande2", domande.get(i - 1).getAnswer());
                                                Log.v("elimina3", String.valueOf(elimina));
                                                DatabaseReference game = ref.child("GameTransfer").child(indexLobby).child("game");
                                                game.child(String.valueOf(elimina)).setValue(false);
                                                for (int c = 0; c < textViewList.size(); c++) {
                                                    if (textViewList.get(c).getText().equals(username)) {
                                                        helpRef.child(String.valueOf(c)).child(("aiuto3")).setValue(true);
                                                        help3.setClickable(false);
                                                        help3.setEnabled(false);
                                                        help3.setBackgroundColor(Color.RED);
                                                    }
                                                }
                                            }
                                            trovato=true;
                                        }else{
                                            trovato=false;
                                        }
                                        break;
                                    case 3:
                                        String opt4= String.valueOf(btnD.getText());
                                        if(!sbagliate.contains(opt4)) {
                                            if (!opt4.equals(domande.get(i - 1).getAnswer())) {
                                                Log.v("domande1", domande.get(i - 1).getOption4());
                                                Log.v("domande2", domande.get(i - 1).getAnswer());
                                                Log.v("elimina4", String.valueOf(elimina));
                                                DatabaseReference game = ref.child("GameTransfer").child(indexLobby).child("game");
                                                game.child(String.valueOf(elimina)).setValue(false);
                                                for (int c = 0; c < textViewList.size(); c++) {
                                                    if (textViewList.get(c).getText().equals(username)) {
                                                        helpRef.child(String.valueOf(c)).child(("aiuto3")).setValue(true);
                                                        help3.setClickable(false);
                                                        help3.setEnabled(false);
                                                        help3.setBackgroundColor(Color.RED);
                                                    }
                                                }
                                            }
                                            trovato = true;
                                        }else {
                                            trovato = false;
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
        DatabaseReference gameTransfer = ref.child("GameTransfer").child(indexLobby).child("utenti");
        gameTransfer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int contatore = 0;

                textViewList.clear();
                textViewList.add(quizTransferActivity.findViewById(R.id.txtUser1));
                textViewList.add(quizTransferActivity.findViewById(R.id.txtUser2));
                textViewList.add(quizTransferActivity.findViewById(R.id.txtUser3));
                textViewList.add(quizTransferActivity.findViewById(R.id.txtUser4));

                ArrayList<ImageView> imageViewList=new ArrayList<>();
                imageViewList.add(quizTransferActivity.findViewById(R.id.imgAvatar1));
                imageViewList.add(quizTransferActivity.findViewById(R.id.imgAvatar2));
                imageViewList.add(quizTransferActivity.findViewById(R.id.imgAvatar3));
                imageViewList.add(quizTransferActivity.findViewById(R.id.imgAvatar4));

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
                    utenti.add(new PlayerGameTransfer(username,aiuto1,aiuto2,aiuto3,aiuto4,0,attivo,rispostaSel));
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
        buttonList.add(quizTransferActivity.findViewById(R.id.btn1));
        buttonList.add(quizTransferActivity.findViewById(R.id.btn2));
        buttonList.add(quizTransferActivity.findViewById(R.id.btn3));
        buttonList.add(quizTransferActivity.findViewById(R.id.btn4));

        textViewList.clear();
        textViewList.add(quizTransferActivity.findViewById(R.id.txtUser1));
        textViewList.add(quizTransferActivity.findViewById(R.id.txtUser2));
        textViewList.add(quizTransferActivity.findViewById(R.id.txtUser3));
        textViewList.add(quizTransferActivity.findViewById(R.id.txtUser4));


        if (!opzione.equals("")) {
            if(opzione.equals(risposta)){
                DatabaseReference bottoneRef = ref.child("GameTransfer").child(indexLobby).child("game").child(String.valueOf(bottone));
                bottoneRef.setValue(true);
                DatabaseReference scoreRef = ref.child("GameTransfer").child(indexLobby).child("utenti");
                for(int a=0;a<textViewList.size();a++){
                    if(textViewList.get(a).getText().equals(username)){
                        if(a==0) {
                            String ResId = "txtAvatar"+1;
                            int id = quizTransferActivity.getResources().getIdentifier(ResId, "id", quizTransferActivity.getPackageName());
                            TextView txtPunteggio = quizTransferActivity.findViewById(id);
                            int punteggio=Integer.parseInt((String)txtPunteggio.getText());
                            punteggio++;
                            txtPunteggio.setText(String.valueOf(punteggio));
                            scoreRef.child("0").child("score").setValue(String.valueOf(punteggio));
                        }else if(a==1) {
                            String ResId = "txtAvatar"+2;
                            int id = quizTransferActivity.getResources().getIdentifier(ResId, "id", quizTransferActivity.getPackageName());
                            TextView txtPunteggio = quizTransferActivity.findViewById(id);
                            int punteggio=Integer.parseInt((String)txtPunteggio.getText());
                            punteggio++;
                            txtPunteggio.setText(String.valueOf(punteggio));
                            scoreRef.child("1").child("score").setValue(String.valueOf(punteggio));
                        }else if(a==2) {
                            String ResId = "txtAvatar"+3;
                            int id = quizTransferActivity.getResources().getIdentifier(ResId, "id", quizTransferActivity.getPackageName());
                            TextView txtPunteggio = quizTransferActivity.findViewById(id);
                            int punteggio=Integer.parseInt((String)txtPunteggio.getText());
                            punteggio++;
                            txtPunteggio.setText(String.valueOf(punteggio));
                            scoreRef.child("2").child("score").setValue(String.valueOf(punteggio));
                        }else{
                            String ResId = "txtAvatar"+4;
                            int id = quizTransferActivity.getResources().getIdentifier(ResId, "id", quizTransferActivity.getPackageName());
                            TextView txtPunteggio = quizTransferActivity.findViewById(id);
                            int punteggio=Integer.parseInt((String)txtPunteggio.getText());
                            punteggio++;
                            txtPunteggio.setText(String.valueOf(punteggio));
                            scoreRef.child("3").child("score").setValue(String.valueOf(punteggio));
                        }
                    }
                }
            }else{
                DatabaseReference bottoneRef = ref.child("GameTransfer").child(indexLobby).child("game").child(String.valueOf(bottone));
                bottoneRef.setValue(false);
                DatabaseReference activePlayerRef=ref.child("GameTransfer").child(indexLobby).child("utenti");

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
        sbagliate.clear();
        DatabaseReference bottoneRef = ref.child("GameTransfer").child(indexLobby).child("game");
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
                                    sbagliate.add(String.valueOf(btnA.getText()));
                                    break;
                                case 1:
                                    btnB.setBackgroundColor(Color.RED);
                                    sbagliate.add(String.valueOf(btnB.getText()));

                                    break;
                                case 2:
                                    btnC.setBackgroundColor(Color.RED);
                                    sbagliate.add(String.valueOf(btnC.getText()));

                                    break;
                                case 3:
                                    btnD.setBackgroundColor(Color.RED);
                                    sbagliate.add(String.valueOf(btnD.getText()));

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
        DatabaseReference scoreRef = ref.child("GameTransfer").child(indexLobby).child("utenti");
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
        DatabaseReference bottoneRef = ref.child("GameTransfer").child(indexLobby).child("game");
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
    }
    public void checkHelp(){
        DatabaseReference helpRef = ref.child("GameTransfer").child(indexLobby).child("utenti");
        helpRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    if(ds.child("username").getValue().equals(username)){
                        Boolean aiuto1 = (Boolean) ds.child("aiuto1").getValue();
                        Boolean aiuto2=(Boolean) ds.child("aiuto2").getValue();
                        Boolean aiuto3=(Boolean) ds.child("aiuto2").getValue();
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
        ImageButton indizio1 = quizTransferActivity.findViewById(R.id.btnIndizio1);
        indizio1.setEnabled(false);
        ImageButton indizio2 = quizTransferActivity.findViewById(R.id.btnIndizio2);
        indizio2.setEnabled(false);
        ImageButton indizio3 = quizTransferActivity.findViewById(R.id.btnIndizio3);
        indizio3.setEnabled(false);
        DatabaseReference utentiRef = ref.child("GameTransfer").child(indexLobby).child("utenti");
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
                    for(a=0;a<punteggi.size();a++){
                        if(nomiUtente.get(a).equals(username)){
                            puntClassifica=punteggi.get(a);
                            if(usernameVincitore.equals(username)){
                                AlertDialog alertDialog;
                                alertDialog=new AlertDialog.Builder(quizTransferActivity).setTitle("Result")
                                        .setMessage(username+" sei il vincitore, il tuo punteggio e' di: "+puntClassifica).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=new Intent(quizTransferActivity, GameActivity.class);
                                        quizTransferActivity.startActivity(intent);
                                    }
                                },2000);
                            }else{
                                AlertDialog alertDialog;
                                alertDialog=new AlertDialog.Builder(quizTransferActivity).setTitle("Result")
                                        .setMessage(username+" non hai vinto, il tuo punteggio è di: "+puntClassifica).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=new Intent(quizTransferActivity,GameActivity.class);
                                        quizTransferActivity.startActivity(intent);
                                    }
                                },2000);
                            }
                        }
                        DatabaseReference classificaRef=ref.child("LeaderBoardTransfer").child(username);
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
