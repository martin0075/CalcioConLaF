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
    LobbyActivity lobbyActivity=LobbyActivity.this;

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

        domandeThread=new DomandeThread(lobbyActivity, domande);


        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("seconds remaining: " + millisUntilFinished / 1000);
                // logic to set the EditText could go here
            }

            public void onFinish() {
                lobbyThread=new LobbyThread(username,usernameLobby,indexLobby,lobbyActivity);
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
                                    Intent intent4=new Intent(LobbyActivity.this, QuizStadium.class);
                                    intent4.putExtra("Username", username);
                                    intent4.putExtra("IndexLobby", indexLobby);
                                    domandeThread.setGame();
                                    if(!domandeThread.isAlive()){
                                        domande=domandeThread.getDomande();
                                        Log.v("size", String.valueOf(domande.size()));
                                    }

                                    startActivity(intent4);
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
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
            }
        }.start();
    }

    public void setGame(){
        DatabaseReference ref = database.getReference();
        DatabaseReference gameStadiumRef = ref.child("GameStadium");

        int n;


        for(b =0; b <5; b++){
            n=r.nextInt(947);

            Quiz quiz=new Quiz();
            String URL = "https://v3.football.api-sports.io/venues?id="+n;
            //creo una coda di richiesta
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            //creo la richiesta
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject result = new JSONObject(response);


                        if(result.getString("results").equals("1")){
                            Log.v("Log", result.toString()+"a");


                            JSONObject result1 = (JSONObject) result.getJSONArray("response").get(0);

                            quiz.setUrlImage(result1.getString("image"));
                            quiz.setAnswer(result1.getString("name"));

                            quiz.setOption4(result1.getString("name"));
                            quiz.setCity(result1.getString("city"));
                            quiz.setCountry(result1.getString("country"));

                            domande.add(quiz);


                        }
                        else{
                            b--;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("Error",error.toString());
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("x-rapidapi-key", "b25b06be0210744411a4ecfbb153f347");
                    params.put("x-rapidapi-host", "v3.football.api-sports.io");

                    return params;
                }
            };
            //aggiungo la richiesta alla coda
            requestQueue.add(stringRequest);


        }


    }

    public void setOption(){
        Log.v("option",domande.size()+"a");
        for(c = 0; c<domande.size(); c++){
            for(a=0; a<3; a++){
                int num=r.nextInt(947);

                String URL1 = "https://v3.football.api-sports.io/venues?id="+num;
                //creo una coda di richiesta
                RequestQueue requestQueue1 = Volley.newRequestQueue(LobbyActivity.this);

                StringRequest stringRequest1 = new StringRequest(Request.Method.GET, URL1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject result2 = new JSONObject(response);
                            if(result2.getString("results").equals("1")){
                                JSONObject result3 = (JSONObject) result2.getJSONArray("response").get(0);
                                Log.v("option",result3.getString("name")+"a");
                                Log.v("count", String.valueOf(a));
                                if((!(domande.get(c).getOption1().equals(result3.getString("name"))))
                                && (!(domande.get(c).getOption2().equals(result3.getString("name"))))
                                && (!(domande.get(c).getOption3().equals(result3.getString("name"))))
                                && (!(domande.get(c).getOption4().equals(result3.getString("name"))))){
                                    Log.v("option",result3.getString("name")+"a");
                                    Log.v("count", String.valueOf(a));
                                    switch(a){
                                        case 0:
                                            domande.get(LobbyActivity.this.b).setOption1(result3.getString("name"));
                                            break;
                                        case 1:
                                            domande.get(LobbyActivity.this.b).setOption2(result3.getString("name"));
                                            break;
                                        case 2:
                                            domande.get(LobbyActivity.this.b).setOption3(result3.getString("name"));
                                            Log.v("option3",result3.getString("name"));
                                            domande.add(domande.get(LobbyActivity.this.b));
                                            Log.v("size", String.valueOf(domande.size()));
                                            break;
                                    }

                                }
                                else{
                                    a--;
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Error",error.toString());
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("x-rapidapi-key", "b25b06be0210744411a4ecfbb153f347");
                        params.put("x-rapidapi-host", "v3.football.api-sports.io");

                        return params;
                    }
                };
                //aggiungo la richiesta alla coda

                requestQueue1.add(stringRequest1);
            }
        }
    }







    }
}