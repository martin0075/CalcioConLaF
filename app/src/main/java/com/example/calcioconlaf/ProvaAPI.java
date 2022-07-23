package com.example.calcioconlaf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProvaAPI extends AppCompatActivity {
    String risposta="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova_api);

        String URL = "https://media.api-sports.io/football/venues/556.png";
        //creo una coda di richiesta
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //creo la richiesta
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                risposta+=response;
                Log.v("Prova",risposta);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Error",error.toString());
            }
        });
        //aggiungo la richiesta alla coda
        requestQueue.add(stringRequest);

        ImageView img=findViewById(R.id.imageStadio);
        Picasso.get().load(URL).into(img);
    }
}