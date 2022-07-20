package com.example.calcioconlaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    private String TAG="Bella";
    private ArrayList<String> listaUser=new ArrayList();
    private ArrayList<String> listaMail=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText username=findViewById(R.id.txtUsername);
        EditText email=findViewById(R.id.txtMail);
        EditText password=findViewById(R.id.txtPassword);
        Button register=findViewById(R.id.btnCreate);
        DatabaseReference ref = database.getReference();
        DatabaseReference usersRef = ref.child("Users");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome=username.getText().toString();
                String mail=email.getText().toString();
                String pw=password.getText().toString();
                User user=new User(nome,pw, mail);
                usersRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listaUser.clear();
                        for(DataSnapshot ds: snapshot.getChildren()) {
                            listaUser.add(ds.child("Username").getValue().toString());
                            listaMail.add(ds.child("Email").getValue().toString());
                        }

                        if((!listaMail.contains(mail))&&(!listaUser.contains(nome))&&((!nome.matches(""))&&(!pw.matches(""))&&(!mail.matches("")))) {
                            usersRef.child(mail).setValue(user);
                            Toast.makeText(RegisterActivity.this,"Registrazione effettuata",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else
                        {
                            Toast.makeText(RegisterActivity.this,"Dati mancanti o già utilizzati",Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
}