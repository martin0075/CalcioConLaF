package com.example.calcioconlaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LobbyActivity extends AppCompatActivity {

    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");

    String username;
    Boolean trovati;
    String usernameLobby;
    String indexLobby;
    LobbyActivity lobbyActivity=LobbyActivity.this;
    int a;
    int b;
    int c;
    Random r=new Random();
    ArrayList<Quiz> domande=new ArrayList<Quiz>();
    DomandeThread domandeThread;
    HomeFragment home=new HomeFragment();
    LobbyThread lobbyThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        TextView timer=findViewById(R.id.txtTimer);

        Intent intent=getIntent();
        username=intent.getStringExtra("Username");
        Intent intent2=getIntent();
        usernameLobby=intent2.getStringExtra("UsernameLobby");
        Intent intent3=getIntent();
        indexLobby=intent3.getStringExtra("IndexLobby");

        //domandeThread=new DomandeThread(lobbyActivity, domande, username, indexLobby);


        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("seconds remaining: " + millisUntilFinished / 1000);
                // logic to set the EditText could go here
            }

            public void onFinish() {
                lobbyThread=new LobbyThread(username,usernameLobby,indexLobby,lobbyActivity, domande);
                lobbyThread.start();

                /*DatabaseReference ref = database.getReference();
                DatabaseReference lobbyStadiumRef = ref.child("LobbyStadium");
                trovati=false;
                lobbyStadiumRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(username==null){
                         username=usernameLobby;
                        }

                        for(DataSnapshot ds: snapshot.getChildren()){
                            Log.d("Bella3", String.valueOf(ds.hasChild(username)));
                            if(ds.hasChild(username)){
                                Log.d("Bella2",String.valueOf(ds.getChildrenCount()));
                                if(ds.getChildrenCount()>1){
                                    trovati= true;

                                    while(!domandeThread.isReceiveData()){
                                        Log.v("isLoaded", String.valueOf(domandeThread.isReceiveData()));
                                    }

                                }else{
                                    if(snapshot.getChildrenCount()==1){
                                        lobbyStadiumRef.child("").setValue("");
                                        lobbyStadiumRef.child(ds.getKey()).removeValue();
                                    }else{
                                        lobbyStadiumRef.child(ds.getKey()).removeValue();
                                    }
                                    Intent intent5=new Intent(LobbyActivity.this, GameActivity.class);
                                    intent5.putExtra("UsernameLobby",username);
                                    startActivity(intent5);
                                }
                            }
                        }

                        if(trovati){


                            domandeThread.start();

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
            }
        }.start();
    }










}