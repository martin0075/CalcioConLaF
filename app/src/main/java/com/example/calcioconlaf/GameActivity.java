package com.example.calcioconlaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class GameActivity extends AppCompatActivity {

    BottomNavigationView nav;

    HomeFragment home=new HomeFragment();
    SettingFragment setting=new SettingFragment();
    LeaderboardFragment leaderboard=new LeaderboardFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        nav=findViewById(R.id.navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.conteiner,home);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.conteiner, home).commit();
                        return true;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.conteiner, setting).commit();
                        return true;
                    case R.id.leaderboards:
                        getSupportFragmentManager().beginTransaction().replace(R.id.conteiner, leaderboard).commit();
                        return true;

                }
                return false;
            }
        });
    }
}