package com.example.projectpuncakphoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    Button btn_edtProfile,btn_logout;
    TextView txt_email,txt_phoneNo,txt_fullname;

    FirebaseUser user;
    DatabaseReference reference;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btn_edtProfile = findViewById(R.id.btn_editprofile);
        btn_logout = findViewById(R.id.btn_logout);

        txt_email = findViewById(R.id.txt_email);
        txt_phoneNo = findViewById(R.id.txt_phone);
        txt_fullname = findViewById(R.id.txt_fullname);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://projectpuncak-2e271-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                RegisterHelper userProfile = snapshot.getValue(RegisterHelper.class);
//
//                if (userProfile != null){
//                    String email = userProfile.email;
//                    String fullName = userProfile.fullname;
//                    String username = userProfile.username;
//                    String phoneNo = userProfile.phoneNo;
//                    String date = userProfile.date;
//                    String gender = userProfile.gender;
//
//                    txt_email.setText(email);
//                    txt_fullname.setText(fullName);
//                    txt_phoneNo.setText(phoneNo);
//                }
                txt_email.setText(snapshot.child("email").getValue().toString());
                txt_fullname.setText(snapshot.child("fullname").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this,"Something wrong happened!",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void callLogoutToLogin(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent next = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(next);
    }

}