package com.example.projectpuncakphoto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ForgetPasswordActivity extends AppCompatActivity {

    ImageButton btnBack;
    TextView titleText;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        btnBack = findViewById(R.id.forget_btn_back);
        titleText = findViewById(R.id.forget_title);
        btnNext = findViewById(R.id.forget_btn_next);
    }

    public void callMakeSelectionScreen(View view) {

        Intent next = new Intent(getApplicationContext(),MakeSelectionActivity.class);

        //add Transition
        Pair[] pairs = new Pair[3];

        pairs[0] = new Pair<View,String>(btnBack,"transition_btn_back");
        pairs[1] = new Pair<View,String>(btnNext,"transition_btn_next");
        pairs[2] = new Pair<View,String>(titleText,"transition_title_txt");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ForgetPasswordActivity.this,pairs);
        startActivity(next,options.toBundle());

    }
}