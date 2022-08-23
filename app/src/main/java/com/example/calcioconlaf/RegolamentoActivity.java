package com.example.calcioconlaf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class RegolamentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regolamento);

        TextView regolamento=findViewById(R.id.textReg);
        regolamento.setText("L'applicazione CalcioConLaF si compone di due giochi: 'GameStadiums' e 'GameTransfers'.\n" +
                "Entrambi i giochi sono pensati per un minimo di 2 giocatori fino ad un massimo di 4 giocatori.\n" +
                "Entrambi i giochi sono caratterizzati da 10 domande che variano da partita a partita ma che sono uguali per tutti gli utenti in gioco.\n" +
                "I giocatori all'interno della partita metteranno alla prova le loro conoscenze calcistiche indovinando le domande proposte, il gioco termina quando le " +
                "10 domande saranno terminate.\n" +
                "Alla fine del gioco il giocatore che avrà totalizzato il punteggio piu' alto sarà decretato vincitore.\n" +
                "Durante la partita i giocatori potranno usufruire di 3 indizi: una volta che un giocatore ne avrà utilizzato uno, non potrà più utilizzare " +
                "quella tipologia di indizio per tutto il corso della partita.");
        regolamento.setMovementMethod(new ScrollingMovementMethod());
    }
}