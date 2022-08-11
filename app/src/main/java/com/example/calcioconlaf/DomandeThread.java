package com.example.calcioconlaf;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class DomandeThread extends Thread{
    LobbyActivity lobbyActivity;
    ArrayList<Quiz> domande;
    String username;
    String indexLobby;
    boolean receiveData=false;
    int b;
    int c;
    int a;
    int cont=0;
    Random r=new Random();
    RequestQueue requestQueue;
    JSONObject result;

    public DomandeThread(LobbyActivity lobbyActivity, ArrayList<Quiz> domande, String username, String indexLobby) {
        this.lobbyActivity=lobbyActivity;
        this.domande=domande;
        this.username=username;
        this.indexLobby=indexLobby;
    }

    @Override
    public void run() {
        setGame(domande);
    }

    public void setGame(ArrayList<Quiz> domande){
        int n;
        requestQueue=Volley.newRequestQueue(lobbyActivity);
        while(cont<=10) {
                n = r.nextInt(500);
                Quiz quiz = new Quiz();
                String URL = "https://v3.football.api-sports.io/venues?id=" + n;
                //creo la richiesta
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            result= new JSONObject(response);

                            //Log.v("V","execute");
                            if (result.getString("results").equals("1")) {
                                Log.v("Log", result.toString() + "a");


                                JSONObject result1 = (JSONObject) result.getJSONArray("response").get(0);

                                quiz.setUrlImage(result1.getString("image"));
                                quiz.setAnswer(result1.getString("name"));

                                quiz.setOption4(result1.getString("name"));
                                quiz.setCity(result1.getString("city"));
                                quiz.setCountry(result1.getString("country"));


                                domande.add(quiz);

                                if (domande.size() > 9) {
                                    RisposteThread risposte = new RisposteThread(username, indexLobby, domande, lobbyActivity);
                                    lobbyActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            risposte.start();
                                            Log.v("Lunghezza",String.valueOf(domande.size()));
                                        }
                                    });

                                }
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
                        params.put("x-rapidapi-key", "b25b06be0210744411a4ecfbb153f347");
                        params.put("x-rapidapi-host", "v3.football.api-sports.io");

                        return params;
                    }
                };

                //aggiungo la richiesta alla coda
                //creo una coda di richiesta

                requestQueue.add(stringRequest);
                cont++;
        }
    }
    public ArrayList<Quiz> getDomande() {
        return domande;
    }
}
