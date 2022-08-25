package com.example.calcioconlaf.GameTransfer;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetButtonThreadTransfer extends Thread{
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference gameTransferRef = ref.child("GameTransfer");
    String indexLobby;

    public SetButtonThreadTransfer(String indexLobby) {
        this.indexLobby = indexLobby;
    }

    @Override
    public void run() {
        super.run();
        settaBottoni();
    }
    public void settaBottoni(){
        DatabaseReference bottoneRef = ref.child("GameTransfer").child(indexLobby).child("game");
        bottoneRef.child("0").setValue("null");
        bottoneRef.child("1").setValue("null");
        bottoneRef.child("2").setValue("null");
        bottoneRef.child("3").setValue("null");
    }
}
