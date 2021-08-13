package com.example.projectpuncakphoto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Register2ndActivity extends AppCompatActivity {

    ImageView btnBack;
    Button btnNext, btnLogin;
    TextView titleText;
    RadioGroup gender;
    RadioButton selectedGender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2nd);

        btnBack = findViewById(R.id.signup_btn_back);
        btnNext = findViewById(R.id.signup_btn_next);
        btnLogin = findViewById(R.id.signup_btn_login);
        titleText = findViewById(R.id.signup_title_txt);

        gender = findViewById(R.id.gender);
        datePicker = findViewById(R.id.age_picker);
    }

    public void callNextSignupScreen(View view) {

        if (!validateGender() | !validateAge()) {
            return;
        }

        selectedGender = findViewById(gender.getCheckedRadioButtonId());
        String gender = selectedGender.getText().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String _date = day + "/" + month + "/" + year;

        String email = getIntent().getStringExtra("email");
        String fullname = getIntent().getStringExtra("fullname");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        Intent next = new Intent(getApplicationContext(), Register3ndActivity.class);

        next.putExtra("email", email);
        next.putExtra("fullname", fullname);
        next.putExtra("username", username);
        next.putExtra("password", password);
        next.putExtra("gender", gender);
        next.putExtra("date", _date);

        //add Transition
        Pair[] pairs = new Pair[4];

        pairs[0] = new Pair<View, String>(btnBack, "transition_btn_back");
        pairs[1] = new Pair<View, String>(btnNext, "transition_btn_next");
        pairs[2] = new Pair<View, String>(btnLogin, "transition_btn_login");
        pairs[3] = new Pair<View, String>(titleText, "transition_title_txt");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register2ndActivity.this, pairs);
        startActivity(next, options.toBundle());
    }

    private boolean validateGender() {
        if (gender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();

        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 17) {
            Toast.makeText(this, "Yout are not eligible to apply", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
