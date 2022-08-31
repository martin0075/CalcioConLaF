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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DomandeThreadTransfer extends Thread{
    LobbyActivityTransfer lobbyActivity;
    ArrayList<QuizTransfer> domande;
    String username;
    String indexLobby;
    int cont=0;
    int x=0;
    int y;
    int [] endPointVuoto;
    ArrayList<String> risposte=new ArrayList<>();
    Random r=new Random();
    RequestQueue requestQueue;
    JSONObject result;
    int i=0;
    int contaElementi=0;
    public DomandeThreadTransfer(LobbyActivityTransfer lobbyActivity, ArrayList<QuizTransfer> domande, String username, String indexLobby,int[] endPointVuoto) {
        this.lobbyActivity = lobbyActivity;
        this.domande = domande;
        this.username = username;
        this.indexLobby = indexLobby;
        this.endPointVuoto=endPointVuoto;
    }

    @Override
    public void run() {
        super.run();
        setDomande(domande);
        //controlla();
    }
    public void setDomande(ArrayList<QuizTransfer> domande){
        ArrayList<String> ids=new ArrayList<>();
        requestQueue= Volley.newRequestQueue(lobbyActivity);
        for(i=0;i<domande.size();i++){
            String id=domande.get(i).getId();

            Log.v("id", String.valueOf(id));
            String URL = "https://api-football-v1.p.rapidapi.com/v3/transfers?player=" + id;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        result = new JSONObject(response);
                        String newId= result.getJSONObject("parameters").getString("player");
                        for(int z=0;z<10;z++){
                            if(newId.equals(domande.get(z).getId())){
                                Log.v("result2", String.valueOf(result));
                                JSONObject result1 = (JSONObject) result.getJSONArray("response").get(0);
                                JSONObject result2=(JSONObject) result1.getJSONArray("transfers").get(0);
                                String data=result2.getString("date");
                                String risposta=result2.getJSONObject("teams").getJSONObject("in").getString("name");
                                String idTeam=result2.getJSONObject("teams").getJSONObject("in").getString("id");
                                Log.v("idTeam",idTeam);
                                domande.get(z).setIdTeam(idTeam);
                                Log.v("idddddd",domande.get(z).getIdTeam());
                                domande.get(z).setAnswer(risposta);
                                String nome=result1.getJSONObject("player").getString("name");
                                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(data);
                                int anno= date1.getYear()+1900;
                                Date d=new Date();
                                int corrente=d.getYear()+1900;
                                if(date1.getMonth()==0){
                                    if(anno>corrente){
                                        String domanda="Dove si trasferira' "+nome+" nella sessione invernale del "+anno+" ?";
                                        domande.get(z).setDomanda(domanda);
                                    }else{
                                        String domanda="Dove si e' trasferito "+nome+" nella sessione invernale del "+anno+" ?";
                                        domande.get(z).setDomanda(domanda);
                                    }
                                }else{
                                    if(anno>corrente){
                                        String domanda="Dove si trasferira' "+nome+" nella sessione estiva del "+anno+" ?";
                                        domande.get(z).setDomanda(domanda);
                                    }else{
                                        String domanda="Dove si e' trasferito "+nome+" nella sessione estiva del "+anno+" ?";
                                        domande.get(z).setDomanda(domanda);
                                    }
                                }
                                z=10;
                            }
                        }
                    } catch (JSONException | ParseException e) {
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
        if(domande.size()==10){
            Log.v("sizeeee", String.valueOf(ids.size()));
            RisposteThreadTransfer risposteThreadTransfer=new RisposteThreadTransfer(username,indexLobby,domande,lobbyActivity);
            lobbyActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    risposteThreadTransfer.start();
                    Log.v("Lunghezza", String.valueOf(domande.size()));
                }
            });
        }
    }
    public void controlla(){
        requestQueue= Volley.newRequestQueue(lobbyActivity);
        for(y=850;y<1010;y++){
            String URL = "https://api-football-v1.p.rapidapi.com/v3/teams?id="+y;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject result=new JSONObject(response);
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
