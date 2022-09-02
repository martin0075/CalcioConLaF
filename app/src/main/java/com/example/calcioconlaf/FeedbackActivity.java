package com.example.calcioconlaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity {
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        RatingBar rating=findViewById(R.id.ratingBar);
        EditText recensioneTxt=findViewById(R.id.recensione);

            Button confirm=findViewById(R.id.confirm);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Recensione recensioneObj = new Recensione();
                    int punteggio= (int) rating.getRating();
                    String recensione= String.valueOf(recensioneTxt.getText());
                    if((!recensione.equals("")) && (punteggio!=0)) {
                        recensioneObj.setRecensione(recensione);
                        recensioneObj.setPunteggio(punteggio);
                        ref.child("Recensioni").child("").setValue(recensioneObj);
                        Intent intent=new Intent(FeedbackActivity.this,GameActivity.class);
                        intent.putExtra("Username",username);
                        startActivity(intent);
                    }
                }
            });
    }
}