package com.example.calcioconlaf;

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

public class DomandeThread extends Thread{

    LobbyActivity lobbyActivity;
    ArrayList<Quiz> domande;
    int b;
    Random r=new Random();

    public DomandeThread(LobbyActivity lobbyActivity, ArrayList<Quiz> domande) {
        this.lobbyActivity=lobbyActivity;
        this.domande=domande;
    }

    @Override
    public void run() {

    }

    public void setGame(){
        int n;

        for(b =0; b <5; b++){
            n=r.nextInt(947);

            Quiz quiz=new Quiz();
            String URL = "https://v3.football.api-sports.io/venues?id="+n;
            //creo una coda di richiesta
            RequestQueue requestQueue = Volley.newRequestQueue(lobbyActivity);
            //creo la richiesta
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject result = new JSONObject(response);


                        if(result.getString("results").equals("1")){
                            //Log.v("Log", result.toString()+"a");


                            JSONObject result1 = (JSONObject) result.getJSONArray("response").get(0);

                            quiz.setUrlImage(result1.getString("image"));
                            quiz.setAnswer(result1.getString("name"));

                            quiz.setOption4(result1.getString("name"));
                            quiz.setCity(result1.getString("city"));
                            quiz.setCountry(result1.getString("country"));

                            domande.add(quiz);
                            Log.v("Domanda1", domande.get(b).getOption4());



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


    public ArrayList<Quiz> getDomande() {
        return domande;
    }
}
