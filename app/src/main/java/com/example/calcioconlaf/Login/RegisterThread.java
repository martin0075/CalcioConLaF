package com.example.calcioconlaf.Login;



import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterThread extends Thread {
    private ArrayList<String> listaUser = new ArrayList();
    private ArrayList<String> listaMail = new ArrayList();
    public FirebaseDatabase database = FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference usersRef = ref.child("Users");

    String nomeT;
    String mailT;
    String pwT;
    User userT;
    RegisterActivity registerActivity;
    public RegisterThread(String nome, String mail, String pw, User user, RegisterActivity registerActivity) {
        this.nomeT = nome;
        this.mailT = mail;
        this.pwT = pw;
        this.userT = user;
        this.registerActivity= registerActivity;
    }

    @Override
    public void run() {
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaUser.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    listaUser.add(ds.child("Username").getValue().toString());
                    listaMail.add(ds.child("Email").getValue().toString());
                }
                if ((!listaMail.contains(mailT)) && (!listaUser.contains(nomeT)) && ((!nomeT.matches("")) && (!pwT.matches("")) && (!mailT.matches("")))) {
                    Log.d("if","if");
                    usersRef.child(mailT).setValue(userT);
                    registerActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(registerActivity,"Registrazione effettuata",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(registerActivity, LoginActivity.class);
                            registerActivity.startActivity(intent);
                        }
                    });
                }else {
                    Log.d("else","else");
                    registerActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(registerActivity,"Dati mancanti o già utilizzati",Toast.LENGTH_SHORT).show();
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

