package com.example.calcioconlaf;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PartitaThread extends Thread{
    ArrayList<Quiz> domande=new ArrayList<>();
    QuizStadium quizStadium;
    int i;
    int cont=0;

    public PartitaThread(ArrayList<Quiz> domande,QuizStadium quizStadium) {
        this.domande=domande;
        this.quizStadium=quizStadium;
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

}
