package com.example.calcioconlaf;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

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
        getDati();
        return _rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDati();
    }
    public void show() {
        recyclerView=_rootView.findViewById(R.id.recyclerLeaderboard);
        recyclerView.setLayoutManager(mLayoutManager);
        nav=_rootView.findViewById(R.id.navigation);
        mAdapter=new AdapterForLeaderboard(listOfElements,getChildFragmentManager());
        recyclerView.setAdapter(mAdapter);
    }
    public void getDati(){
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
                        for(int j=0;j<listOfElements.size();j++){
                            if(listOfElements.get(j).getPunteggio()>listOfElements.get(i).getPunteggio()){
                                int temp=listOfElements.get(i).getPunteggio();
                                listOfElements.get(i).setPunteggio(listOfElements.get(j).getPunteggio());
                                listOfElements.get(j).setPunteggio(temp);
                            }
                        }
                    }
                    Collections.reverse(listOfElements);
                    show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}