package com.example.calcioconlaf;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderboardFragment} factory method to
 * create an instance of this fragment.
 */
public class LeaderboardFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterForLeaderboard mAdapter;
    SettingFragment setting=new SettingFragment();
    RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(this.getActivity());
    LeaderboardFragment leaderboardFragment=LeaderboardFragment.this;
    BottomNavigationView nav;
    GameActivity gameActivity= (GameActivity) leaderboardFragment.getActivity();
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    ArrayList<LeaderboardElement> listOfElements=new ArrayList<>();
    View _rootView;
    HomeFragment home=new HomeFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (_rootView == null) {
            // Inflate the layout for this fragment
            _rootView = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        } else {
        }



        return _rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnStadium=leaderboardFragment.getView().findViewById(R.id.btnStadium);
        Button btnTransfer=leaderboardFragment.getActivity().findViewById(R.id.btnTransfer);

        btnStadium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDatiStadium();
                btnStadium.setBackgroundColor(Color.GREEN);
                btnTransfer.setBackgroundColor(Color.parseColor("#673AB7"));
            }
        });
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDatiTransfer();
                btnTransfer.setBackgroundColor(Color.GREEN);
                btnStadium.setBackgroundColor(Color.parseColor("#673AB7"));
            }
        });

    }
    public void show() {
        recyclerView=_rootView.findViewById(R.id.recyclerLeaderboard);
        recyclerView.setLayoutManager(mLayoutManager);
        nav=_rootView.findViewById(R.id.navigation);
        mAdapter=new AdapterForLeaderboard(listOfElements,getChildFragmentManager());
        recyclerView.setAdapter(mAdapter);
    }
    public void getDatiStadium(){
        DatabaseReference classificaRef=ref.child("LeaderBoardStadium");
        classificaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int size=Integer.parseInt(String.valueOf(snapshot.getChildrenCount()));
                listOfElements.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    String usernameClass=ds.getKey();
                    int punteggio=Integer.parseInt(String.valueOf(ds.getValue()));
                    Log.v("punteggio", String.valueOf(punteggio));
                    Log.v("username", String.valueOf(usernameClass));
                    LeaderboardElement elemento=new LeaderboardElement(usernameClass,punteggio);
                    listOfElements.add(elemento);
                    //Log.v("elemento", String.valueOf(listOfElements.get(0).getPunteggio()));
                }
                if(listOfElements.size()==size){
                    for(int i=0;i<listOfElements.size();i++){
                        for(int j=i+1;j<listOfElements.size();j++){
                            if(listOfElements.get(j).getPunteggio()>listOfElements.get(i).getPunteggio()){
                                int scoreTemp=listOfElements.get(i).getPunteggio();
                                String userTemp=listOfElements.get(i).getUsername();
                                listOfElements.get(i).setPunteggio(listOfElements.get(j).getPunteggio());
                                listOfElements.get(i).setUsername((listOfElements.get(j).getUsername()));
                                listOfElements.get(j).setPunteggio(scoreTemp);
                                listOfElements.get(j).setUsername(userTemp);
                            }
                        }
                        Log.v("list", listOfElements.get(i).getUsername());
                    }
                    for(int a=0;a<listOfElements.size();a++){
                        Log.v("list2", listOfElements.get(a).getUsername());
                    }
                    show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getDatiTransfer(){
        DatabaseReference classificaRef=ref.child("LeaderBoardTransfer");
        classificaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int size=Integer.parseInt(String.valueOf(snapshot.getChildrenCount()));
                listOfElements.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    String usernameClass=ds.getKey();
                    int punteggio=Integer.parseInt(String.valueOf(ds.getValue()));
                    Log.v("punteggio", String.valueOf(punteggio));
                    Log.v("username", String.valueOf(usernameClass));
                    LeaderboardElement elemento=new LeaderboardElement(usernameClass,punteggio);
                    listOfElements.add(elemento);
                    //Log.v("elemento", String.valueOf(listOfElements.get(0).getPunteggio()));
                }
                if(listOfElements.size()==size){
                    for(int i=0;i<listOfElements.size();i++){
                        for(int j=i+1;j<listOfElements.size();j++){
                            if(listOfElements.get(j).getPunteggio()>listOfElements.get(i).getPunteggio()){
                                int scoreTemp=listOfElements.get(i).getPunteggio();
                                String userTemp=listOfElements.get(i).getUsername();
                                listOfElements.get(i).setPunteggio(listOfElements.get(j).getPunteggio());
                                listOfElements.get(i).setUsername((listOfElements.get(j).getUsername()));
                                listOfElements.get(j).setPunteggio(scoreTemp);
                                listOfElements.get(j).setUsername(userTemp);
                            }
                        }
                        Log.v("list", listOfElements.get(i).getUsername());
                    }
                    for(int a=0;a<listOfElements.size();a++){
                        Log.v("list2", listOfElements.get(a).getUsername());
                    }
                    show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}