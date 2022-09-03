package com.example.calcioconlaf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        text=findViewById(R.id.textAb);
        text.setText("Ci presentiamo un po'..." +
                "Siamo Martin e Davide, siamo due ragazzi che frequentano il terzo anno del corso di Informatica per il " +
                "Management presso l'Università di Bologna. " +
                "Questa applicazione va incontro alla nostra piu' " +
                "grande passione: il calcio. " +
                "Entrambi siamo molto appassionati di questo sport e giochiamo fin da quando siamo piccoli, perciò" +
                "questa applicazione ci sembrava l'occasione di poter unire l'utile al dilettevole ovvero un progetto universitario" +
                "e il calcio.");
        text.setMovementMethod(new ScrollingMovementMethod());
    }
}