package com.example.calcioconlaf;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StadiumThread extends Thread{
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference lobbyStadiumRef = ref.child("LobbyStadium");
    String username;
    String username2;
    public String indexLobby;
    //Activity activity;
    HomeFragment homefragment;
    public StadiumThread(String username,String username2,HomeFragment homefragment){
        this.username=username;
        this.username2=username2;
        this.homefragment=homefragment;
    }

    @Override
    public void run() {
        lobbyStadiumRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                indexLobby="";
                if(snapshot.getChildrenCount()>0){
                    for(DataSnapshot ds: snapshot.getChildren()) {
                        if(ds.getChildrenCount()<4){
                            indexLobby=ds.getKey();
                            Log.v("IndexLobby1", indexLobby);
                            lobbyStadiumRef.child(indexLobby).child(username).setValue(username);

                        }
                    }
                }
                if(indexLobby.equals("")){
                    Log.v("IndexLobby2", indexLobby);
                    indexLobby= String.valueOf((snapshot.getChildrenCount()+1));
                    Log.v("IndexLobby3", indexLobby);
                    if(username==null) {
                        lobbyStadiumRef.child(indexLobby).child(username2).setValue(username2);
                    }else{
                        lobbyStadiumRef.child(indexLobby).child(username).setValue(username);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(username==null){
            homefragment.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent= new Intent(homefragment.getActivity(), LobbyActivity.class);
                    intent.putExtra("UsernameLobby", username2);
                    intent.putExtra("IndexLobby", indexLobby);
                    homefragment.getActivity().startActivity(intent);
                }
            });
        }else{
            homefragment.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent= new Intent(homefragment.getActivity(), LobbyActivity.class);
                    intent.putExtra("Username", username);
                    intent.putExtra("IndexLobby", indexLobby);
                    homefragment.getActivity().startActivity(intent);
                }
            });
        }
    }
}
