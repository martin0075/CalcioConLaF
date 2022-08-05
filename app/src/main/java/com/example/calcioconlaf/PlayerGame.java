package com.example.calcioconlaf;

public class PlayerGame {

    private String username;
    private boolean aiuto1;
    private boolean aiuto2;
    private boolean aiuto3;
    private boolean aiuto4;
    private int score;
    private boolean activePlayer;

    public PlayerGame(String username, boolean aiuto1, boolean aiuto2, boolean aiuto3, boolean aiuto4, int score, boolean activePlayer) {
        this.username = username;
        this.aiuto1 = aiuto1;
        this.aiuto2 = aiuto2;
        this.aiuto3 = aiuto3;
        this.aiuto4 = aiuto4;
        this.score = score;
        this.activePlayer=activePlayer;
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
