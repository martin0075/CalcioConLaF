package com.example.calcioconlaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();


        Button login= findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                   @Override
                   public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       if (!queryDocumentSnapshots.isEmpty()) {
                           List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                           for (DocumentSnapshot d : list) {

                           }

                       } else {
                           // if the snapshot is empty we are displaying a toast message.
                           Toast.makeText(LoginActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                       }
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       // if we do not get any data or any error we are displaying
                       // a toast message that we do not get any data
                       Toast.makeText(LoginActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
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