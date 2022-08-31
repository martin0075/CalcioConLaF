package com.example.calcioconlaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.calcioconlaf.Login.LoginThread;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GameActivity extends AppCompatActivity {

    BottomNavigationView nav;
    HomeFragment home = new HomeFragment();
    SettingFragment setting = new SettingFragment();
    LeaderboardFragment leaderboard;
    RegolamentoFragment regolamento;

    public FirebaseDatabase database = FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        leaderboard = new LeaderboardFragment();
        regolamento=new RegolamentoFragment();
        setContentView(R.layout.activity_game);

        nav = findViewById(R.id.navigation);

        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        Intent intent2 = getIntent();
        String usernameLobby = intent2.getStringExtra("UsernameLobby");


        Bundle bundle = new Bundle();
        bundle.putString("Username", username);
        bundle.putString("UsernameLobby", usernameLobby);

        home.setArguments(bundle);
        setting.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.conteiner, home).commit();

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.conteiner, home).commit();
                        return true;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.conteiner, setting).commit();
                        return true;
                    case R.id.leaderboards:
                        getSupportFragmentManager().beginTransaction().replace(R.id.conteiner, leaderboard).commit();
                        return true;
                    case R.id.regolamento:
                        getSupportFragmentManager().beginTransaction().replace(R.id.conteiner, regolamento).commit();
                        return true;

                }
                return false;
            }
        });
    }
}
