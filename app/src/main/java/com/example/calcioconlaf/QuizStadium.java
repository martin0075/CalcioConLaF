package com.example.calcioconlaf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QuizStadium extends AppCompatActivity {
    String risposta="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova_api);

        String URL = "https://v3.football.api-sports.io/venues?id=556";
        //creo una coda di richiesta
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //creo la richiesta
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                risposta+=response;
                try {
                    JSONObject result = new JSONObject(response);
                    if(result.getString("results").equals(1)){
                        JSONObject result1 = (JSONObject) result.getJSONArray("response").get(0);
                        String srcImage=result1.getString("image");

                        ImageView img=findViewById(R.id.imageStadio);
                        Picasso.get().load(srcImage).into(img);
                        //Toast.makeText(ProvaAPI.this,result1.getString("name"),Toast.LENGTH_SHORT).show();
                        Log.v("Prova",result1.getString("image"));
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
                params.put("x-rapidapi-key", "c65be7f34d909f82ce5539b6617129b5");
                params.put("x-rapidapi-host", "v3.football.api-sports.io");

                return params;
            }
        };
        //aggiungo la richiesta alla coda
        requestQueue.add(stringRequest);



    }
}