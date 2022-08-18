package com.example.calcioconlaf;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SetButtonThread extends Thread{
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference gameStadiumRef = ref.child("GameStadium");
    String indexLobby;
    public SetButtonThread(String indexLobby) {
        this.indexLobby=indexLobby;
    }

    @Override
    public void run() {
        super.run();
        settaBottoni();
    }
    public void settaBottoni(){
        ArrayList<String> bottoni=new ArrayList<>();
        bottoni.add("null");
        bottoni.add("null");
        bottoni.add("null");
        bottoni.add("null");
        DatabaseReference bottoneRef = ref.child("GameStadium").child(indexLobby).child("game");
        bottoneRef.setValue(bottoni);
    }
}
