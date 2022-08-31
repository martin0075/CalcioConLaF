package com.example.calcioconlaf.GameTransfer;

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

public class FotoThreadTransfer extends Thread{
    LobbyActivityTransfer lobbyActivity;
    ArrayList<QuizTransfer> domande;
    String username;
    String indexLobby;
    int cont=0;
    Random r=new Random();
    RequestQueue requestQueue;
    JSONObject result;
    int x=0;
    int [] endpointVuoto={0,4,11,43,40,50,90,91,92,94,95,100,117,129,137,144,145,160,181,182,222,231,240,247,262,268,281,
            283,293,299,310,339,348,359,367,369,394,398,405,469,472,473,474,476,477,480,482,520,522,
            528,558,576,580,587,588,589,602,631,625,658,659,676,675,661,672,703,711,715,718,734,735,767,779,796,800,806,809,811,816,819,821,830,835,852,859,871,897,903,909,917,919,918,930,
            936,940,947,949,961,999,993,994,998};

    public FotoThreadTransfer(LobbyActivityTransfer lobbyActivity, ArrayList<QuizTransfer> domande, String username, String indexLobby) {
        this.lobbyActivity = lobbyActivity;
        this.domande = domande;
        this.username = username;
        this.indexLobby = indexLobby;
    }

    @Override
    public void run() {
        super.run();
        setFoto(domande);
    }
    public void setFoto(ArrayList<QuizTransfer> domande){
        int n;
        requestQueue= Volley.newRequestQueue(lobbyActivity);
        boolean vuoto=false;
        ArrayList<Integer> presi=new ArrayList<>();
        while(cont<10) {
            n = r.nextInt(1010);
            for (int d = 0; d < endpointVuoto.length; d++) {
                if (n == endpointVuoto[d]) {
                    vuoto = true;
                    d = endpointVuoto.length;
                } else {
                    vuoto = false;
                }
            }
            if (!vuoto) {
                if (!presi.contains(n)) {
                    presi.add(n);
                    QuizTransfer quizTransfer = new QuizTransfer();
                    String URL = "https://api-football-v1.p.rapidapi.com/v3/players?id=" + n + "&" + "season=" + 2020;
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                result = new JSONObject(response);
                                JSONObject result1 = (JSONObject) result.getJSONArray("response").get(0);
                                JSONObject result2 = (JSONObject) result1.get("player");
                                quizTransfer.setId(result2.getString("id"));
                                quizTransfer.setUrlImage(result2.getString("photo"));

                                domande.add(quizTransfer);

                                if (domande.size() == 10) {
                                    DomandeThreadTransfer domandeThreadTransfer = new DomandeThreadTransfer(lobbyActivity, domande, username, indexLobby,endpointVuoto);
                                    lobbyActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            domandeThreadTransfer.start();
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
                    cont++;
                }
            }
        }
    }
    public ArrayList<QuizTransfer> getDomande() {
        return domande;
    }
    public void controlla(){
        requestQueue= Volley.newRequestQueue(lobbyActivity);
        for(x=900;x<1010;x++){
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
            requestQueue.add(stringRequest);
            cont++;
        }
    }
}
