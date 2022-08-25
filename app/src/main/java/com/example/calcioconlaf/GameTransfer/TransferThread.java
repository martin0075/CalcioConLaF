package com.example.calcioconlaf.GameTransfer;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.calcioconlaf.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TransferThread extends Thread{
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference lobbyTransferRef = ref.child("LobbyTransfer");
    String usernameTransfer;
    String username2Transfer;
    public String indexLobbyTransfer;
    //Activity activity;
    HomeFragment homefragment;
    public TransferThread(String username,String username2,HomeFragment homefragment) {
        this.usernameTransfer=username;
        this.username2Transfer=username2;
        this.homefragment=homefragment;
    }

    @Override
    public void run() {
        lobbyTransferRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                indexLobbyTransfer="";
                if(snapshot.getChildrenCount()>0){
                    for(DataSnapshot ds: snapshot.getChildren()) {
                        if(ds.getChildrenCount()<4){
                            indexLobbyTransfer=ds.getKey();
                            Log.v("IndexLobby1", indexLobbyTransfer);
                            lobbyTransferRef.child(indexLobbyTransfer).child(usernameTransfer).setValue(usernameTransfer);
                            FragmentToActivityThreadTransfer fragmentToActivityThread=new FragmentToActivityThreadTransfer(usernameTransfer,indexLobbyTransfer,homefragment);
                            fragmentToActivityThread.start();

                        }
                    }
                }
                if(indexLobbyTransfer.equals("")){
                    Log.v("IndexLobby2", indexLobbyTransfer);
                    indexLobbyTransfer= String.valueOf((snapshot.getChildrenCount()+1));
                    Log.v("IndexLobby3", indexLobbyTransfer);
                    if(usernameTransfer==null) {
                        lobbyTransferRef.child(indexLobbyTransfer).child(username2Transfer).setValue(username2Transfer);
                        FragmentToActivityThreadTransfer fragmentToActivityThread=new FragmentToActivityThreadTransfer(usernameTransfer,indexLobbyTransfer,homefragment);
                        fragmentToActivityThread.start();

                    }else{
                        lobbyTransferRef.child(indexLobbyTransfer).child(usernameTransfer).setValue(usernameTransfer);
                        FragmentToActivityThreadTransfer fragmentToActivityThread=new FragmentToActivityThreadTransfer(usernameTransfer,indexLobbyTransfer,homefragment);
                        fragmentToActivityThread.start();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
