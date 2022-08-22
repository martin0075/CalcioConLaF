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
    int [] endpointVuoto;
    int c;
    int a;
    int cont=0;
    int n=0;
    Random r=new Random();
    RequestQueue requestQueue1;
    ArrayList<String> opzioni=new ArrayList<>();


    public RisposteThread(String username, String indexLobby, ArrayList<Quiz> domande, LobbyActivity lobbyActivity, int [] endpointVuoto) {
        this.username=username;
        this.indexLobby=indexLobby;
        this.domande=domande;
        this.lobbyActivity=lobbyActivity;
        this.endpointVuoto=endpointVuoto;
    }

    @Override
    public void run() {
        setOption(domande);
    }

    public void setOption(ArrayList<Quiz> domande){
        requestQueue1=Volley.newRequestQueue(lobbyActivity);
        while(c<domande.size()){

            cont++;

            for(a=0; a<3; a++) {
                int num = r.nextInt(947);
                boolean vuoto=false;
                for(int d=0; d<endpointVuoto.length;d++){
                    if(num==endpointVuoto[d]){
                        vuoto=true;
                        a--;
                        d=endpointVuoto.length;
                    }
                    else{
                        vuoto=false;
                    }
                }
                if(!vuoto){
                    String URL1 = "https://v3.football.api-sports.io/venues?id=" + num;
                    //creo una coda di richiesta
                    //RequestQueue requestQueue1 = Volley.newRequestQueue(this);

                    Log.v("Domanda", domande.get(c).getAnswer());
                    StringRequest stringRequest1 = new StringRequest(Request.Method.GET, URL1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject result2 = new JSONObject(response);
                                JSONObject result3=(JSONObject) result2.getJSONArray("response").get(0);
                                opzioni.add(result3.getString("name"));
                                if(opzioni.size()==30){
                                    SetRisposteThread setRisposte=new SetRisposteThread(domande, opzioni,lobbyActivity,username,indexLobby);
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
}
