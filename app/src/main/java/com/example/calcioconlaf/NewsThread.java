package com.example.calcioconlaf;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsThread extends Thread{
    NewsActivity newsActivity;
    AdapterForNews mAdapter;
    public NewsThread(NewsActivity newsActivity,AdapterForNews mAdapter){
        this.newsActivity=newsActivity;
        this.mAdapter=mAdapter;
    }
    public void callApi(String url, ArrayList<NewsElement> listOfElements, RecyclerView recyclerView) {
        RequestQueue requestQueue = Volley.newRequestQueue(newsActivity);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result = new JSONArray(response);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject result1 = (JSONObject) result.get(i);
                        Log.v("Prova", result1.getString("PublisherDate"));
                        listOfElements.add(new NewsElement(result1.getString("PublisherDate"), result1.getString("Title"), result1.getString("Image"), result1.getString("PublisherName"), result1.getString("NewsLink")));
                    }
                    mAdapter = new AdapterForNews(listOfElements, newsActivity::onNewsClick);
                    recyclerView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Error", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-rapidapi-key", "bedb2fe714msh252489034ac87a7p162fc5jsnd07235a8b8a6");
                params.put("x-rapidapi-host", "football98.p.rapidapi.com");

                return params;
            }
        };
        //aggiungo la richiesta alla coda
        requestQueue.add(stringRequest);

        mAdapter = new AdapterForNews(listOfElements, newsActivity::onNewsClick);
        recyclerView.setAdapter(mAdapter);
    }
}