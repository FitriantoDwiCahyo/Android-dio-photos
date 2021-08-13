package com.example.projectpuncakphoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Button btnForgetPass,btnLogin,btnRegister;
    ProgressBar progressBar;
    TextInputLayout email,password;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        btnForgetPass = findViewById(R.id.btn_forgetpassword);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_signup);

        email = findViewById(R.id.inputemail);
        password = findViewById(R.id.inputpassword);

        auth = FirebaseAuth.getInstance();
    }


    public void callHomeScreen(View view){
        if(!validateEmail() | !validatePassword()){
            return;
        }

        String _email = email.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(_email,_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()){
                        Intent next = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(next);
                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this,"Check your email to verify your account!",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }else {
                    Toast.makeText(LoginActivity.this,"Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void callForgetPassScreen(View view) {

        Intent forgetPass = new Intent(getApplicationContext(),ForgetPasswordActivity.class);

        startActivity(forgetPass);

    }

    public void callRegisterScreen(View view){
        Intent register = new Intent(getApplicationContext(),RegisterActivity.class);

        Pair[] pairs = new Pair[1];

        pairs[0] = new Pair<View,String>(findViewById(R.id.btn_signup),"transition_register");

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
            startActivity(register, options.toBundle());
        }else {
            startActivity(register);
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(val).matches()){
            email.setError("Please enter a valid Email");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
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