package com.example.projectpuncakphoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splashscreen();
    }

    public void splashscreen(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Tostarted = new Intent(MainActivity.this,GetStatedActivity.class);
                startActivity(Tostarted);
                finish();
            }
        },1500); //2000 ms = 2 detik
    }
}