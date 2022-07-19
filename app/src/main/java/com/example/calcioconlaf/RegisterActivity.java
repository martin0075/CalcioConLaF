package com.example.calcioconlaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseReference database;
    private String TAG="Bella";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText username=findViewById(R.id.txtUsername);
        EditText email=findViewById(R.id.txtMail);
        EditText password=findViewById(R.id.txtPassword);
        Button register=findViewById(R.id.btnCreate);
        database = FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome=username.getText().toString();
                String mail=email.getText().toString();
                String pw=password.getText().toString();
                Log.v(TAG,"Utente creato"+" "+nome+" "+mail+" "+pw);
                database.child("Users").child("Username").setValue(nome);
                database.child("Users").child("Email").setValue(mail);
                database.child("Users").child("Password").setValue(pw);

                Intent register=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(register);


            }
        });
    }
}