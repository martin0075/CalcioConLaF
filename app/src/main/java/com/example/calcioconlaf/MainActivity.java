package com.example.calcioconlaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.calcioconlaf.Login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button game = findViewById(R.id.btnGame);

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
        Button news = findViewById(R.id.btnNews);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent news=new Intent(MainActivity.this, NewsActivity.class);
                startActivity(news);
            }
        });
    }




}