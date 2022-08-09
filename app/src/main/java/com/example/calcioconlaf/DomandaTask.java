package com.example.calcioconlaf;

import android.os.AsyncTask;
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

import java.util.HashMap;
import java.util.Map;

public class DomandaTask extends AsyncTask<Void, Void, JSONObject> {
    JSONObject result;
    LobbyActivity lobbyActivity;
    int n;

    public DomandaTask(LobbyActivity lobbyActivity, int n) {
        this.lobbyActivity = lobbyActivity;
        this.n=n;
    }


    @Override
    protected JSONObject doInBackground(Void... voids) {


        String URL = "https://v3.football.api-sports.io/venues?id="+n;



        //creo la richiesta
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    result = new JSONObject(response);

                    Log.v("result1", result.toString());



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
        //creo una coda di richiesta
        RequestQueue requestQueue = Volley.newRequestQueue(lobbyActivity);
        requestQueue.add(stringRequest);

        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        //Log.v("result1", jsonObject.toString());
    }
}
