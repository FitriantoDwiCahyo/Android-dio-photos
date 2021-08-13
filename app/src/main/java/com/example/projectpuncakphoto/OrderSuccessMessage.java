package com.example.projectpuncakphoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class OrderSuccessMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success_message);

        orderSuccess();
    }

    public void orderSuccess(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Tostarted = new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(Tostarted);
                finish();
            }
        },2000); //2000 ms = 2 detik
    }
}