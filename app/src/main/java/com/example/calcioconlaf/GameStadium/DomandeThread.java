package com.example.calcioconlaf.GameStadium;

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
    int [] endpointVuoto={0,51,53,70,74,108,131,138,141,154,156,179,187,191,196,212,229,263,266,267,294,305,358,394,436,439,447,455,
            469,480,486,492,498,499,505,506,520,520,521,524,528,531,534,540,541,544,548,549,552,561,564,575,576,580,590,591,595,613,622,667,669,
            670,677,696,709,716,717,719,721,727,729,736,743,744,772,804,808,828,869,883,915,916,990,6,8,11,13,18,30,35,47,56,60,72,77,85,86,89,92,
            106,129,133,137,143,158,165,169,208,209,213,221,222,239,240,245,246,249,250,257,258,264,270,273,
            272,274,275,280,291,293,295,304,306,309,314,325,330,335,344,343,349,354,352,359,362,363,364,365,382,387,389,395,396,397,400,
            405,408,407,418,430,444,459,470,482,491,511,529,530,553,557,563,584,593,607,629,640,648,688,763,767,770,773,
            774,781,785,787,794,796,795,797,798,802,806,809,812,816,843,847,848,851,856,857,859,858,862,
            867,868,877,886,914,827,949,950,967,981,988,994,995,997,998,1000,1001,1007};
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
                    Quiz quiz = new Quiz();
                    String URL = "https://api-football-v1.p.rapidapi.com/v3/venues?id=" + n;
                    //creo la richiesta
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                result = new JSONObject(response);


                                Log.v("Log", result.toString() + "a");


                                JSONObject result1 = (JSONObject) result.getJSONArray("response").get(0);

                                quiz.setUrlImage(result1.getString("image"));
                                quiz.setAnswer(result1.getString("name"));


                                quiz.setCity(result1.getString("city"));
                                quiz.setCountry(result1.getString("country"));
                                quiz.setOption1("");
                                quiz.setOption2("");
                                quiz.setOption3("");
                                quiz.setOption4("");


                                domande.add(quiz);

                                if (domande.size() == 15) {
                                    RisposteThread risposte = new RisposteThread(username, indexLobby, domande, lobbyActivity, endpointVuoto);
                                    lobbyActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            risposte.start();
                                            Log.v("Lunghezza", String.valueOf(domande.size()));
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
                            params.put("x-rapidapi-key", "c728752071msh66c91d630cb7b30p106f24jsnd0ef66ad62b5");
                            params.put("x-rapidapi-host", "api-football-v1.p.rapidapi.com");

                            return params;
                        }
                    };

                    //aggiungo la richiesta alla coda
                    //creo una coda di richiesta

                    requestQueue.add(stringRequest);
                    cont++;
                }
            }
        }
    }

    public ArrayList<Quiz> getDomande() {
        return domande;
    }
}
