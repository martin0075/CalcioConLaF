package com.example.calcioconlaf.GameTransfer;

public class PlayerGameTransfer {

    private String username;
    private boolean aiuto1;
    private boolean aiuto2;
    private boolean aiuto3;
    private boolean aiuto4;
    private int score;
    private boolean activePlayer;
    private String rispostaSel;
    private String  stoppato;

    public PlayerGameTransfer(String username, boolean aiuto1, boolean aiuto2, boolean aiuto3, boolean aiuto4, int score, boolean activePlayer,String rispostaSel,String stoppato) {
        this.username = username;
        this.aiuto1 = aiuto1;
        this.aiuto2 = aiuto2;
        this.aiuto3 = aiuto3;
        this.aiuto4 = aiuto4;
        this.score = score;
        this.activePlayer=activePlayer;
        this.rispostaSel=rispostaSel;
        this.stoppato=stoppato;
    }

    public String getStoppato() {
        return stoppato;
    }

    public void setStoppato(String stoppato) {
        this.stoppato = stoppato;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAiuto1() {
        return aiuto1;
    }

    public boolean isAiuto2() {
        return aiuto2;
    }

    public boolean isAiuto3() {
        return aiuto3;
    }

    public boolean isAiuto4() {
        return aiuto4;
    }

    public int getScore() {
        return score;
    }

    public boolean isActivePlayer() {
        return activePlayer;
    }

    public String getRispostaSel() {
        return rispostaSel;
    }

    public void setRispostaSel(String rispostaSel) {
        this.rispostaSel = rispostaSel;
    }

    public void setActivePlayer(boolean activePlayer) {

        this.activePlayer = activePlayer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAiuto1(boolean aiuto1) {
        this.aiuto1 = aiuto1;
    }

    public void setAiuto2(boolean aiuto2) {
        this.aiuto2 = aiuto2;
    }

    public void setAiuto3(boolean aiuto3) {
        this.aiuto3 = aiuto3;
    }

    public void setAiuto4(boolean aiuto4) {
        this.aiuto4 = aiuto4;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
