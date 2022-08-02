package com.example.calcioconlaf;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    public String username;
    public Username user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username=getArguments().getString("Username");
        user=new Username(username);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton imgBtnStadium=getView().findViewById(R.id.imgBtnStadium);
        ImageButton imgBtnTransfer=getView().findViewById(R.id.imgBtnTransfer);

        imgBtnStadium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = database.getReference();
                DatabaseReference lobbyStadiumRef = ref.child("LobbyStadium");


                lobbyStadiumRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String indexLobby="";
                        if(snapshot.getChildrenCount()>0){
                            for(DataSnapshot ds: snapshot.getChildren()) {
                                if(ds.getChildrenCount()<4){
                                    indexLobby=ds.getKey();
                                    Log.v("Index 1", indexLobby);
                                    lobbyStadiumRef.child(indexLobby).child(username).setValue(username);

                                }
                            }
                        }
                        if(indexLobby.equals("")){
                            indexLobby= String.valueOf((snapshot.getChildrenCount()+1));
                            Log.v("Index 2", indexLobby);
                            lobbyStadiumRef.child(indexLobby).child(username).setValue(username);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent2=new Intent(getActivity(),LobbyActivity.class);
                intent2.putExtra("Username", username);
                startActivity(intent2);

                Intent intent= new Intent(getActivity(), LobbyActivity.class);
                startActivity(intent);
            }
        });

        imgBtnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private class Username{
        public String username;
        public Username(){}
        public Username(String username){
            this.username=username;
        }

    }
}