package com.example.calcioconlaf.Login;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.calcioconlaf.GameActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginThread extends Thread{
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    private String TAG="Boh";
    private ArrayList lista=new ArrayList();
    DatabaseReference ref = database.getReference();
    DatabaseReference usersRef = ref.child("Users");
    String dati;
    String username;
    LoginActivity loginActivity;


    public LoginThread(String dati,String username,LoginActivity loginActivity){
        this.dati=dati;
        this.loginActivity=loginActivity;
        this.username=username;
    }
    @Override
    public void run() {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lista.clear();
                for(DataSnapshot ds: snapshot.getChildren()) {
                    lista.add(ds.child("Username").getValue()+" "+ds.child("Password").getValue());
                }
                if(lista.contains(dati)){
                    loginActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(loginActivity,"Dati corretti",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(loginActivity, GameActivity.class);
                            intent.putExtra("Username", username);
                            loginActivity.startActivity(intent);
                        }
                    });
                }else{
                    loginActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(loginActivity,"Dati mancanti o non corretti",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
