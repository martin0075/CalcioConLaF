package com.example.calcioconlaf.GameTransfer;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetIndiziThread extends Thread{
    ArrayList<QuizTransfer> domande;
    RequestQueue requestQueue;
    int i;
    String indexLobby;
    String username;
    LobbyActivityTransfer lobbyActivity;
    int contatore=0;
    JSONObject result1;
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference gameTransfer = ref.child("GameTransfer");
    DatabaseReference lobbyRef=ref.child("LobbyTransfer");

    public SetIndiziThread(ArrayList<QuizTransfer> domande,LobbyActivityTransfer lobbyActivity,String indexLobby,String username) {
        this.domande=domande;
        this.lobbyActivity=lobbyActivity;
        this.username=username;
        this.indexLobby=indexLobby;
    }
    @Override
    public void run() {
        super.run();
        setIndizio(domande);
    }
    public void setIndizio(ArrayList<QuizTransfer> domande) {
        requestQueue= Volley.newRequestQueue(lobbyActivity);
        for (i = 0; i < domande.size(); i++) {
            String id = domande.get(i).getIdTeam();
            String URL2 = "https://api-football-v1.p.rapidapi.com/v3/teams?id=" + id;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        result1 = new JSONObject(response);
                        JSONObject result2 = (JSONObject) result1.getJSONArray("response").get(0);
                        //JSONArray arr = new JSONArray(result2.getString("response"));
                        JSONObject jObj=result2.getJSONObject("team");
                        String newId = result2.getJSONObject("team").getString("id");
                        for (int z = 0; z < 10; z++) {
                            if (newId.equals(domande.get(z).getIdTeam())) {
                                contatore++;
                                String citta = result2.getJSONObject("venue").getString("city");
                                String nazione = jObj.getString("country");
                                domande.get(z).setCity(citta);
                                domande.get(z).setCountry(nazione);
                                //Log.v("domande",domande.get(z).getCity());
                                //Log.v("domande",domande.get(z).getCountry());
                                z=10;
                                write();
                            }
                        }
                            } catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("x-rapidapi-key", "c728752071msh66c91d630cb7b30p106f24jsnd0ef66ad62b5");
                    params.put("x-rapidapi-host", "api-football-v1.p.rapidapi.com");

                    return params;
                }
            };
            requestQueue.add(stringRequest);
        };
    }
    public void write(){
        gameTransfer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gameTransfer.child(indexLobby).child("domande").setValue(domande);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lobbyActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent4=new Intent(lobbyActivity, QuizTransferActivity.class);
                                intent4.putExtra("Username", username);
                                intent4.putExtra("IndexLobby", indexLobby);
                                intent4.putExtra("Domande",domande);
                                lobbyActivity.startActivity(intent4);
                            }
                        });
                    }
                },5000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lobbyRef.child(indexLobby).setValue(null);
    }
}
