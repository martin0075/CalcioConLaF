package com.example.calcioconlaf;

import java.io.Serializable;

public class Recensione implements Serializable {
    String recensione;
    float punteggio;

    public Recensione(){}

    public Recensione(String recensione, int punteggio) {
        this.recensione = recensione;
        this.punteggio = punteggio;
    }

    public String getRecensione() {
        return recensione;
    }

    public void setRecensione(String recensione) {
        this.recensione = recensione;
    }

    public float getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(float punteggio) {
        this.punteggio = punteggio;
    }
}
