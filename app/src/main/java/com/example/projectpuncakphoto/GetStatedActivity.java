package com.example.projectpuncakphoto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStatedActivity extends AppCompatActivity {

    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_stated);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView image = findViewById(R.id.imageView);
        TextView txt_welcome = findViewById(R.id.textView);
        Button btn_signin = findViewById(R.id.btn_signin);
        Button btn_signup = findViewById(R.id.btn_signup);


        image.setAlpha(v);
        txt_welcome.setAlpha(v);
        btn_signin.setAlpha(v);
        btn_signup.setAlpha(v);

        image.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        txt_welcome.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        btn_signin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        btn_signup.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(700).start();


    }

    public void callLoginScreen(View view){
        Intent login = new Intent(getApplicationContext(),LoginActivity.class);

        Pair[] pairs = new Pair[1];

        pairs[0] = new Pair<View,String>(findViewById(R.id.btn_signin),"transition_login");

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GetStatedActivity.this, pairs);
            startActivity(login, options.toBundle());
        }else {
            startActivity(login);
        }
    }

    public void callRegisterScreen(View view){
        Intent register = new Intent(getApplicationContext(),RegisterActivity.class);

        Pair[] pairs = new Pair[1];

        pairs[0] = new Pair<View,String>(findViewById(R.id.btn_signup),"transition_register");

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GetStatedActivity.this, pairs);
            startActivity(register, options.toBundle());
        }else {
            startActivity(register);
        }
    }
}