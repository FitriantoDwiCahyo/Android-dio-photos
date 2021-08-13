package com.example.projectpuncakphoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterSuccessMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success_message);
    }

    public void callLoginScreen(View view) {
        Intent gotologin = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(gotologin);
    }
}