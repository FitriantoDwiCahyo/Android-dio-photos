package com.example.projectpuncakphoto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    //Variabel
    ImageView btnBack;
    Button btnNext, btnLogin;
    TextView titleText;

    //Get Data Varibles
    TextInputLayout email, fullname, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Hooks for animation
        btnBack = findViewById(R.id.signup_btn_back);
        btnNext = findViewById(R.id.signup_btn_next);
        btnLogin = findViewById(R.id.signup_btn_login);
        titleText = findViewById(R.id.signup_title_txt);

        //Hooks for getting data
        fullname = findViewById(R.id.register_fullname);
        email = findViewById(R.id.register_email);
        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_password);

    }

    public void callNextSignupScreen(View view) {

        if(!validateEmail() | !validateFullname() | !validateUsername() | !validatePassword()){
            return;
        }

        String _email = email.getEditText().getText().toString().trim();
        String _fullname = fullname.getEditText().getText().toString().trim();
        String _username = username.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        Intent next = new Intent(getApplicationContext(), Register2ndActivity.class);

        next.putExtra("email",_email);
        next.putExtra("fullname",_fullname);
        next.putExtra("username",_username);
        next.putExtra("password",_password);

        //add Transition
        Pair[] pairs = new Pair[4];

        pairs[0] = new Pair<View, String>(btnBack, "transition_btn_back");
        pairs[1] = new Pair<View, String>(btnNext, "transition_btn_next");
        pairs[2] = new Pair<View, String>(btnLogin, "transition_btn_login");
        pairs[3] = new Pair<View, String>(titleText, "transition_title_txt");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
        startActivity(next, options.toBundle());
    }

    /*
    validation functions
     */

    private boolean validateFullname() {
        String val = fullname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullname.setError("Field can't be empty");
            return false;
        } else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        } else if(!val.matches(checkEmail)){
            email.setError("Invalid Email");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            username.setError("Field can't be empty");
            return false;
        } else if(val.length()>20) {
            username.setError("Username is too large");
            return false;
        } else if(!val.matches(checkSpaces)){
            username.setError("No white spaces are allowed");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^"+
                //"(?=.*[0-9]"+ //at least 1 digit
                //"(?=.*[a-z])"+ // at least 1 lower case letter
                //"(?=.*[A-Z])"+ // at least 1 upper case letter
                "(?=.*[a-zA-Z])" + // any letter
                "(?=.*[@#$%^&+=])" + //at least 1 special character
                "(?=\\S*$)" + // no white spaces
                ".{4,}" + // at least 4 character
                "$";

        if (val.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        } else if(!val.matches(checkPassword)){
            password.setError("Password should contain 4 characters!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}