package com.example.calcioconlaf;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.EnglishReasonPhraseCatalog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsThread extends Thread{
    NewsActivity newsActivity;
    AdapterForNews mAdapter;
    public static int TIMEOUT_MS=20000;

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
                    JSONObject result=new JSONObject(response);
                    JSONArray result1=result.getJSONArray("news");
                    JSONObject result3=result.getJSONObject("share");
                    Log.v("result",String.valueOf(result1));
                    for (int i = 0; i < result1.length(); i++) {
                        JSONObject result2 = (JSONObject) result1.get(i);
                        listOfElements.add(new NewsElement(result2.getString("newsDate"), result2.getString("newsHeadline"), result2.getString("newsFirstImage"), result2.getString("newsSource"), result3.getString("url")));
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
                params.put("x-rapidapi-key", "c728752071msh66c91d630cb7b30p106f24jsnd0ef66ad62b5");
                params.put("x-rapidapi-host", "transfermarket.p.rapidapi.com");

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //aggiungo la richiesta alla coda
        requestQueue.add(stringRequest);

        mAdapter = new AdapterForNews(listOfElements, newsActivity::onNewsClick);
        recyclerView.setAdapter(mAdapter);
    }
}
