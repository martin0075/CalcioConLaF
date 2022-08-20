package com.example.calcioconlaf;

public class LeaderboardElement {
    private String username;
    private int punteggio;
    public LeaderboardElement(String username,int punteggio){
        this.punteggio=punteggio;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

}