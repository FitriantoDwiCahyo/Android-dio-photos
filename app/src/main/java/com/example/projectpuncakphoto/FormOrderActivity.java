package com.example.projectpuncakphoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class FormOrderActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    TextView hargaclass,txtPrewed;
    TextInputLayout orderName,orderLocation,orderClass;
    DatePicker datePicker;

    String[] option = {"Bronze","Silver","Gold"};
    ArrayAdapter<String> arrayAdapter;

    FirebaseAuth auth;

    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference1;

    String userID;

    Integer codeOrder = new Random().nextInt();

    String USERCODEORDER="usercodeorder";
    String usercodeorder_key ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_order);

        auth = FirebaseAuth.getInstance();

        hargaclass = findViewById(R.id.hargaclass);
        txtPrewed = findViewById(R.id.txt_prewed);

        orderName = findViewById(R.id.order_name);
        orderLocation = findViewById(R.id.order_location);
        orderClass = findViewById(R.id.order_class);

        datePicker = findViewById(R.id.age_picker);

        autoCompleteTextView = findViewById(R.id.opsipaket);

        arrayAdapter = new ArrayAdapter<>(this,R.layout.dropdownpaket,option);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(arrayAdapter.getItem(i).equals("Bronze")){
                    hargaclass.setText("Rp.1.800.000");
                }else if (arrayAdapter.getItem(i).equals("Silver")){
                    hargaclass.setText("Rp.2.100.000");
                }else {
                    hargaclass.setText("Rp.3.000.000");
                }
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://projectpuncak-2e271-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderName.setPlaceholderText(snapshot.child("fullname").getValue().toString());
                orderName.setHint(snapshot.child("fullname").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void callOrderSuccess(View view) {
        txtPrewed.setText("prewedding");
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String date = day + "/" + month + "/" + year;

        String price = hargaclass.getText().toString().trim();
        String fullname = orderName.getEditText().getText().toString().trim();
        String location = orderLocation.getEditText().getText().toString().trim();
        String classPackage = orderClass.getEditText().getText().toString().trim();

        String auth = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String userCodeOrder = auth+txtPrewed.getText().toString()+codeOrder;

        DataOrder dataOrder = new DataOrder(date,fullname,location,classPackage,price,userCodeOrder);
        FirebaseDatabase.getInstance("https://projectpuncak-2e271-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Orders")
                .child(auth).child(userCodeOrder).setValue(dataOrder)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            SharedPreferences sharedPreferences = getSharedPreferences(USERCODEORDER,MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit() ;
                            editor.putString(usercodeorder_key,userCodeOrder);
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "Success Order,Thanks a lot!", Toast.LENGTH_SHORT).show();
                            Intent next = new Intent(getApplicationContext(),OrderSuccessMessage.class);
                            startActivity(next);

                        }else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}