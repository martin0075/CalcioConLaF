package com.example.calcioconlaf;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsActivity extends AppCompatActivity implements AdapterForNews.OnNewsListerner{
    ArrayList<NewsElement> listOfElements;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = findViewById(R.id.recycler);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        listOfElements = new ArrayList<NewsElement>();
        Button btnPremierLeague=findViewById(R.id.btnPremier);
        btnPremierLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listOfElements.clear();
                String URL = "https://football98.p.rapidapi.com/premierleague/news";
                //creo una coda di richiesta
                RequestQueue requestQueue = Volley.newRequestQueue(NewsActivity.this);
                //creo la richiesta
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray result=new JSONArray(response);
                            for(int i=0;i<result.length();i++){
                                JSONObject result1 = (JSONObject) result.get(i);
                                listOfElements.add(new NewsElement(result1.getString("PublisherDate"),result1.getString("Title"),result1.getString("Image"),result1.getString("PublisherName"), result1.getString("NewsLink")));
                            }
                            mAdapter = new AdapterForNews(listOfElements, NewsActivity.this::onNewsClick);
                            recyclerView.setAdapter(mAdapter);
                            //Log.v("Prova",result1.getString("image"));
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
                        params.put("x-rapidapi-key", "bedb2fe714msh252489034ac87a7p162fc5jsnd07235a8b8a6");
                        params.put("x-rapidapi-host", "football98.p.rapidapi.com");

                        return params;
                    }
                };
                //aggiungo la richiesta alla coda
                requestQueue.add(stringRequest);
                mAdapter = new AdapterForNews(listOfElements, NewsActivity.this::onNewsClick);
                recyclerView.setAdapter(mAdapter);

            }
        });
        Button btnSerieA=findViewById(R.id.btnSerieA);
        btnSerieA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listOfElements.clear();
                String URL = "https://football98.p.rapidapi.com/seriea/news";
                //creo una coda di richiesta
                RequestQueue requestQueue = Volley.newRequestQueue(NewsActivity.this);
                //creo la richiesta
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray result=new JSONArray(response);
                            for(int i=0;i<result.length();i++){
                                JSONObject result1 = (JSONObject) result.get(i);
                                listOfElements.add(new NewsElement(result1.getString("PublisherDate"),result1.getString("Title"),result1.getString("Image"),result1.getString("PublisherName"), result1.getString("NewsLink")));
                            }
                            mAdapter = new AdapterForNews(listOfElements, NewsActivity.this::onNewsClick);
                            recyclerView.setAdapter(mAdapter);
                            //Log.v("Prova",result1.getString("image"));
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
                        params.put("x-rapidapi-key", "bedb2fe714msh252489034ac87a7p162fc5jsnd07235a8b8a6");
                        params.put("x-rapidapi-host", "football98.p.rapidapi.com");

                        return params;
                    }
                };
                //aggiungo la richiesta alla coda
                requestQueue.add(stringRequest);
                mAdapter = new AdapterForNews(listOfElements, NewsActivity.this::onNewsClick);
                recyclerView.setAdapter(mAdapter);

            }
        });
        Button btnBundesliga=findViewById(R.id.btnBundesliga);
        btnBundesliga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listOfElements.clear();
                String URL = "https://football98.p.rapidapi.com/bundesliga/news";
                //creo una coda di richiesta
                RequestQueue requestQueue = Volley.newRequestQueue(NewsActivity.this);
                //creo la richiesta
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray result=new JSONArray(response);
                            for(int i=0;i<result.length();i++){
                                JSONObject result1 = (JSONObject) result.get(i);
                                listOfElements.add(new NewsElement(result1.getString("PublisherDate"),result1.getString("Title"),result1.getString("Image"),result1.getString("PublisherName"), result1.getString("NewsLink")));
                            }
                            mAdapter = new AdapterForNews(listOfElements, NewsActivity.this::onNewsClick);
                            recyclerView.setAdapter(mAdapter);
                            //Log.v("Prova",result1.getString("image"));
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
                        params.put("x-rapidapi-key", "bedb2fe714msh252489034ac87a7p162fc5jsnd07235a8b8a6");
                        params.put("x-rapidapi-host", "football98.p.rapidapi.com");

                        return params;
                    }
                };
                //aggiungo la richiesta alla coda
                requestQueue.add(stringRequest);
                mAdapter = new AdapterForNews(listOfElements, NewsActivity.this::onNewsClick);
                recyclerView.setAdapter(mAdapter);

            }
        });
        Button btnLiga=findViewById(R.id.btnLiga);
        btnLiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listOfElements.clear();
                String URL = "https://football98.p.rapidapi.com/liga/news";
                //creo una coda di richiesta
                RequestQueue requestQueue = Volley.newRequestQueue(NewsActivity.this);
                //creo la richiesta
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray result=new JSONArray(response);
                            for(int i=0;i<result.length();i++){
                                JSONObject result1 = (JSONObject) result.get(i);
                                listOfElements.add(new NewsElement(result1.getString("PublisherDate"),result1.getString("Title"),result1.getString("Image"),result1.getString("PublisherName"), result1.getString("NewsLink")));
                            }
                            mAdapter = new AdapterForNews(listOfElements, NewsActivity.this::onNewsClick);
                            recyclerView.setAdapter(mAdapter);
                            //Log.v("Prova",result1.getString("image"));
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
                        params.put("x-rapidapi-key", "bedb2fe714msh252489034ac87a7p162fc5jsnd07235a8b8a6");
                        params.put("x-rapidapi-host", "football98.p.rapidapi.com");

                        return params;
                    }
                };
                //aggiungo la richiesta alla coda
                requestQueue.add(stringRequest);
                mAdapter = new AdapterForNews(listOfElements, NewsActivity.this::onNewsClick);
                recyclerView.setAdapter(mAdapter);

            }
        });
        Button btnLigue1=findViewById(R.id.btnLigue1);
        btnLigue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listOfElements.clear();
                String URL = "https://football98.p.rapidapi.com/ligue1/news";
                //creo una coda di richiesta
                RequestQueue requestQueue = Volley.newRequestQueue(NewsActivity.this);
                //creo la richiesta
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray result=new JSONArray(response);
                            for(int i=0;i<result.length();i++){
                                JSONObject result1 = (JSONObject) result.get(i);
                                listOfElements.add(new NewsElement(result1.getString("PublisherDate"),result1.getString("Title"),result1.getString("Image"),result1.getString("PublisherName"), result1.getString("NewsLink")));
                            }
                            mAdapter = new AdapterForNews(listOfElements, NewsActivity.this::onNewsClick);
                            recyclerView.setAdapter(mAdapter);
                            //Log.v("Prova",result1.getString("image"));
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
                        params.put("x-rapidapi-key", "bedb2fe714msh252489034ac87a7p162fc5jsnd07235a8b8a6");
                        params.put("x-rapidapi-host", "football98.p.rapidapi.com");

                        return params;
                    }
                };
                //aggiungo la richiesta alla coda
                requestQueue.add(stringRequest);
                mAdapter = new AdapterForNews(listOfElements, NewsActivity.this::onNewsClick);
                recyclerView.setAdapter(mAdapter);

            }
        });
    }



    @Override
    public void onNewsClick(int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listOfElements.get(position).getLink()));
        startActivity(browserIntent);
    }
}