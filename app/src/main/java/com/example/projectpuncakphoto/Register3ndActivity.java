package com.example.projectpuncakphoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class Register3ndActivity extends AppCompatActivity {

    FirebaseAuth auth;

    ImageButton btnBack;
    Button btnSignup,btnLogin;
    TextView titleText,emailtxt;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3nd);

        auth = FirebaseAuth.getInstance();

        btnBack = findViewById(R.id.signup_btn_back);
        btnSignup = findViewById(R.id.signup_btn_signup);
        btnLogin = findViewById(R.id.signup_btn_login);
        titleText = findViewById(R.id.signup_title_txt);

        phoneNumber = findViewById(R.id.signup_phone_number);
        countryCodePicker = findViewById(R.id.codeNumber);

    }

    public boolean validatePhoneNumber(){
        String val = phoneNumber.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            phoneNumber.setError("Field can't be empty");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }

    }

    public void callLoginScreen(View view) {
        if(!validatePhoneNumber()){
            return;
        }

        //Get all values passed from previos screens using intent
        String fullname = getIntent().getStringExtra("fullname");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        String date = getIntent().getStringExtra("date");
        String gender = getIntent().getStringExtra("gender");

        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String phoneNo = "+"+countryCodePicker.getFullNumber()+_getUserEnteredPhoneNumber;

        //progressBar.setVisibility(view,VISIBLE);
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            RegisterHelper register = new RegisterHelper(email,fullname,username,phoneNo,date,gender);

                            FirebaseDatabase.getInstance("https://projectpuncak-2e271-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(register).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register3ndActivity.this,"User has been registered successfully!",Toast.LENGTH_SHORT).show();
                                        //        progressBar.setVisibility(view,VISIBLE);

                                    } else {
                                        Toast.makeText(Register3ndActivity.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                                        //        progressBar.setVisibility(view,GONE);
                                    }
                                }
                            });

                            Intent next = new Intent(getApplicationContext(),RegisterSuccessMessage.class);

                            //add Transition
                            Pair[] pairs = new Pair[4];

                            pairs[0] = new Pair<View,String>(btnBack,"transition_btn_back");
                            pairs[1] = new Pair<View,String>(btnSignup,"transition_btn_next");
                            pairs[2] = new Pair<View,String>(btnLogin,"transition_btn_login");
                            pairs[3] = new Pair<View,String>(titleText,"transition_title_txt");

                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register3ndActivity.this,pairs);
                            startActivity(next,options.toBundle());

                        } else {
                            Toast.makeText(Register3ndActivity.this,"Failed to register!",Toast.LENGTH_LONG).show();
                            //        progressBar.setVisibility(view,GONE);
                        }
                    }
                });

    }
}