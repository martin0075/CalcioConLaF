package com.example.calcioconlaf.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.calcioconlaf.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    private String TAG="Boh";
    private ArrayList lista=new ArrayList();
    private LoginThread loginThread;
    LoginActivity loginActivity=LoginActivity.this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username=findViewById(R.id.txtUsername);
        EditText password=findViewById(R.id.txtPassword);

        Button login= findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dati=username.getText()+" "+password.getText();
                loginThread=new LoginThread(dati,username.getText().toString(),loginActivity);
                loginThread.start();
            }
        });

        Button register=findViewById(R.id.btnCreate);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
    }
}