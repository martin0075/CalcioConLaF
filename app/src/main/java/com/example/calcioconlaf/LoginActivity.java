package com.example.calcioconlaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference database;
    private String TAG="Boh";
    //final HashMap mappa=new HashMap();
    private ArrayList lista=new ArrayList();
    //private ArrayList listaPw=new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username=findViewById(R.id.txtUsername);
        EditText password=findViewById(R.id.txtPassword);
        database = FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users");

        Button login= findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        lista.clear();
                        for(DataSnapshot ds: snapshot.getChildren()) {
                            lista.add(ds.child("Username").getValue()+","+ds.child("Password").getValue());
                        }
                        String dati=username.getText()+","+password.getText();
                            if(lista.contains(dati)){
                                Toast.makeText(LoginActivity.this,"Dati corretti",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this,"Errore dati",Toast.LENGTH_SHORT).show();
                            }
                        }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.tbCreate);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);// get the reference of Toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back= new Intent(LoginActivity.this, MainActivity.class);
                startActivity(back);
            }
        });*/
            // Setting/replace toolbar as the ActionBar
       /*toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back= new Intent(LoginActivity.this, MainActivity.class);
                startActivity(back);
            }
        });*/
    }
}