package com.example.calcioconlaf;

import android.content.Intent;
import android.util.Log;

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

public class RisposteThread extends Thread{

    String username;
    String indexLobby;
    ArrayList<Quiz> domande;
    LobbyActivity lobbyActivity;
    int c;
    int a;
    int cont=0;
    int n=0;
    Random r=new Random();
    RequestQueue requestQueue1;
    ArrayList<String> opzioni=new ArrayList<>();
    String opzione1;
    String opzione2;
    String opzione3;

    public RisposteThread(String username, String indexLobby, ArrayList<Quiz> domande, LobbyActivity lobbyActivity) {
        this.username=username;
        this.indexLobby=indexLobby;
        this.domande=domande;
        this.lobbyActivity=lobbyActivity;
    }

    @Override
    public void run() {
        setOption(domande);
    }

    public void setOption(ArrayList<Quiz> domande){
        requestQueue1=Volley.newRequestQueue(lobbyActivity);
        while(c<domande.size()){
            Log.v("size",String.valueOf(domande.size()));
            cont++;
            Log.v("c", String.valueOf(c));
            for(a=0; a<3; a++) {
                int num = r.nextInt(947);
                String URL1 = "https://v3.football.api-sports.io/venues?id=" + num;
                //creo una coda di richiesta
                //RequestQueue requestQueue1 = Volley.newRequestQueue(this);

                    Log.v("Domanda", domande.get(c).getAnswer());
                    StringRequest stringRequest1 = new StringRequest(Request.Method.GET, URL1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject result2 = new JSONObject(response);
                                if (result2.getString("results").equals("1")) {
                                JSONObject result3 = (JSONObject) result2.getJSONArray("response").get(0);
                                //Log.v("result3", result3.getString("name"));
                                opzioni.add(result3.getString("name"));

                                if(opzioni.size()>25){
                                        Log.v("sizeD", String.valueOf(domande.size()));
                                }

                                //Log.v("opt1",domande.get(c).getOption1());
                                /*if ((!(domande.get(c).getOption1().equals(result3.getString("name"))))
                                            && (!(domande.get(c).getOption2().equals(result3.getString("name"))))
                                            && (!(domande.get(c).getOption3().equals(result3.getString("name"))))
                                            && (!(domande.get(c).getOption4().equals(result3.getString("name"))))) {
                                    switch (a) {
                                        case 0:
                                            domande.get(c).setOption1(result3.getString("name"));
                                            Log.v("count0", String.valueOf(a));
                                            Log.v("option1", domande.get(c).getOption1());
                                        break;
                                        case 1:
                                            domande.get(c).setOption2(result3.getString("name"));
                                            Log.v("option2", domande.get(c).getOption2());
                                        break;
                                        case 2:
                                            domande.get(c).setOption3(result3.getString("name"));
                                            Log.v("option3", domande.get(c).getOption3());
                                            //cont++;
                                        break;
                                    }
                                }*/
                                    //else {
                                      //  a--;
                                    //}
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
                    requestQueue1.add(stringRequest1);
                if(a==2){
                    c++;

                }
            }
        }
        if(cont==domande.size()){
            Intent intent4=new Intent(lobbyActivity, QuizStadium.class);
            intent4.putExtra("Username", username);
            intent4.putExtra("IndexLobby", indexLobby);

            lobbyActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lobbyActivity.startActivity(intent4);
                }
            });
        }
    }
}
