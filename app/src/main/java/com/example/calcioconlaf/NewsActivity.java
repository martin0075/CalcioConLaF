package com.example.calcioconlaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements AdapterForNews.OnNewsListerner{
    ArrayList<NewsElement> listOfElements;
    RecyclerView recyclerView;
    AdapterForNews mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    BottomNavigationView nav;
    NewsThread newsThread;
    NewsActivity newsActivity=NewsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = findViewById(R.id.recycler);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        listOfElements = new ArrayList<NewsElement>();
        nav=findViewById(R.id.navigation);

        newsThread=new NewsThread(newsActivity,mAdapter);

        String URL2 = "https://football98.p.rapidapi.com/liga/news";
        //creo una coda di richiesta
        newsThread.callApi(URL2,listOfElements,recyclerView);
        //callApi(URL2,listOfElements,recyclerView);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                listOfElements.clear();

                switch(item.getItemId()){
                    case R.id.premier:
                        String URL = "https://football98.p.rapidapi.com/premierleague/news";
                        newsThread.callApi(URL,listOfElements,recyclerView);
                        return true;
                    case R.id.seriea:
                        String URL1 = "https://football98.p.rapidapi.com/seriea/news";
                        //creo una coda di richiesta
                        newsThread.callApi(URL1,listOfElements,recyclerView);
                        return true;
                    case R.id.liga:
                        String URL2 = "https://football98.p.rapidapi.com/liga/news";
                        //creo una coda di richiesta
                        newsThread.callApi(URL2,listOfElements,recyclerView);
                        return true;
                    case R.id.ligue1:
                        String URL3 = "https://football98.p.rapidapi.com/ligue1/news";
                        newsThread.callApi(URL3,listOfElements,recyclerView);
                        return true;
                    case R.id.bundes:
                        String URL4 = "https://football98.p.rapidapi.com/bundesliga/news";
                        newsThread.callApi(URL4,listOfElements,recyclerView);

                        return true;
                }
                return false;
            }

        });

    }

    /*private void callApi(String url, ArrayList<NewsElement> listOfElements,  RecyclerView recyclerView){
        RequestQueue requestQueue = Volley.newRequestQueue(NewsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result=new JSONArray(response);
                    for(int i=0;i<result.length();i++){
                        JSONObject result1 = (JSONObject) result.get(i);
                        Log.v("Prova",result1.getString("PublisherDate"));
                        listOfElements.add(new NewsElement(result1.getString("PublisherDate"),result1.getString("Title"),result1.getString("Image"),result1.getString("PublisherName"), result1.getString("NewsLink")));
                    }
                    mAdapter = new AdapterForNews(listOfElements, NewsActivity.this::onNewsClick);
                    recyclerView.setAdapter(mAdapter);

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
    }*/

    @Override
    public void onNewsClick(int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listOfElements.get(position).getLink()));
        startActivity(browserIntent);
    }
}