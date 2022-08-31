package com.example.calcioconlaf;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegolamentoFragment} factory method to
 * create an instance of this fragment.
 */
public class RegolamentoFragment extends Fragment {
    RegolamentoFragment regolamentoFragment=RegolamentoFragment.this;
    GameActivity gameActivity= (GameActivity) regolamentoFragment.getActivity();
    View _rootView;
    TextView regText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (_rootView == null) {
            // Inflate the layout for this fragment
            _rootView = inflater.inflate(R.layout.fragment_regolamento, container, false);
        } else {
        }
        show();
        return _rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        show();
    }
    public void show(){
        regText=_rootView.findViewById(R.id.textReg);
        regText.setText("L'applicazione CalcioConLaF si compone di due giochi: 'GameStadiums' e 'GameTransfers'.\n" +
                "Entrambi i giochi sono pensati per un minimo di 2 giocatori fino ad un massimo di 4 giocatori.\n" +
                "Entrambi i giochi sono caratterizzati da 10 domande che variano da partita a partita ma che sono uguali per tutti gli utenti in gioco.\n" +
                "I giocatori all'interno della partita metteranno alla prova le loro conoscenze calcistiche indovinando le domande proposte, il gioco termina quando le " +
                "10 domande saranno terminate.\n" +
                "Alla fine del gioco il giocatore che avrà totalizzato il punteggio piu' alto sarà decretato vincitore.\n" +
                "Durante la partita i giocatori potranno usufruire di 3 indizi: una volta che un giocatore ne avrà utilizzato uno, non potrà più utilizzare " +
                "quella tipologia di indizio per tutto il corso della partita.");
        regText.setMovementMethod(new ScrollingMovementMethod());
    }
}