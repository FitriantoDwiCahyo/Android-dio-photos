package com.example.projectpuncakphoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class HomeActivity extends AppCompatActivity {

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LinearLayout linearLayout = findViewById(R.id.bottom_sheet);

        bottomSheetBehavior =BottomSheetBehavior.from(linearLayout);

        LinearLayout btn_dashboard = findViewById(R.id.btn_dashboard);
        LinearLayout btn_profile = findViewById(R.id.btn_profile);
        ImageButton btn_menu = findViewById(R.id.btn_show);
        CardView btn_wedding = findViewById(R.id.btn_wedding);

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        btn_wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent wedding = new Intent(HomeActivity.this,FormOrderActivity.class);
                startActivity(wedding);
            }
        });

        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(HomeActivity.this,DashboardActivity.class);
                startActivity(dashboard);
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(profile);
            }
        });
    }
}