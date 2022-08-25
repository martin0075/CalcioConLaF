package com.example.calcioconlaf.GameTransfer;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.calcioconlaf.GameStadium.Quiz;
import com.example.calcioconlaf.GameStadium.SetRisposteThread;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RisposteThreadTransfer extends Thread{
    String username;
    String indexLobby;
    ArrayList<QuizTransfer> domande;
    LobbyActivityTransfer lobbyActivity;
    int [] endPointVuoto={};
    int c;
    JSONObject result;
    int a;
    int x=0;
    int cont=0;
    int n=0;
    Random r=new Random();
    RequestQueue requestQueue1;
    RequestQueue requestQueue2;
    ArrayList<String> opzioni=new ArrayList<>();
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference lobbyTransferRef = ref.child("prova");
    public RisposteThreadTransfer(String username, String indexLobby, ArrayList<QuizTransfer> domande, LobbyActivityTransfer lobbyActivity){
        this.username=username;
        this.indexLobby=indexLobby;
        this.domande=domande;
        this.lobbyActivity=lobbyActivity;

    }

    @Override
    public void run() {
        super.run();
        //setOption(domande);
        controlla();
    }
    public void setOption(ArrayList<QuizTransfer> domande) {
        requestQueue1 = Volley.newRequestQueue(lobbyActivity);
        while (c < domande.size()) {

            cont++;

            for (a = 0; a < 3; a++) {
                int num = r.nextInt(1010);
                boolean vuoto = false;
                for (int d = 0; d < endPointVuoto.length; d++) {
                    if (num == endPointVuoto[d]) {
                        vuoto = true;
                        a--;
                        d = endPointVuoto.length;
                    } else {
                        vuoto = false;
                    }
                }
                if (!vuoto) {
                    String URL1 = "https://v3.football.api-sports.io/teams?id=" + num;
                    //creo una coda di richiesta
                    //RequestQueue requestQueue1 = Volley.newRequestQueue(this);

                    StringRequest stringRequest1 = new StringRequest(Request.Method.GET, URL1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject result2 = new JSONObject(response);
                                JSONObject result3=(JSONObject) result2.getJSONArray("response").get(0);
                                String nomeSq=result3.getJSONObject("team").getString("name");
                                opzioni.add(nomeSq);
                                if(opzioni.size()==30){
                                    SetRisposteThreadTransfer setRisposte=new SetRisposteThreadTransfer(domande, opzioni,lobbyActivity,username,indexLobby);
                                    lobbyActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            setRisposte.start();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("Error", error.toString());
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("x-rapidapi-key", "3de8d2d4a7acf04769f77ac5ebd17840");
                            params.put("x-rapidapi-host", "v3.football.api-sports.io");
                            params.put("cont", String.valueOf(c));


                            return params;
                        }
                    };
                    //aggiungo la richiesta alla coda
                    requestQueue1.add(stringRequest1);
                    if(a==2){
                        c++;
                    }
                }
            }
        }
    }
    public void controlla(){
        requestQueue2= Volley.newRequestQueue(lobbyActivity);
        for(x=0;x<250;x++){
            String URL = "https://api-football-v1.p.rapidapi.com/v3/players?id=" + x+"&"+"season="+2020;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        result = new JSONObject(response);
                        if(result.getJSONArray("response").length()==0){
                            Log.v("result1", String.valueOf(result.getJSONArray("parameters").get(0)));
                        }
                        //JSONObject result1 = (JSONObject) result.getJSONArray("response").get(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("x-rapidapi-key", "c728752071msh66c91d630cb7b30p106f24jsnd0ef66ad62b5");
                    params.put("x-rapidapi-host", "api-football-v1.p.rapidapi.com");

                    return params;
                }
            };
            requestQueue2.add(stringRequest);
            cont++;
        }
    }
}
