package com.example.projectpuncakphoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailOrderActivity extends AppCompatActivity {

    TextView detailName,detailPackageClass,detailLocation,
            detailDate,detailPrice;

    Date dateTime;
    DateFormat dateFormat;

    DatabaseReference databaseReference;

    String USERCODEORDER="usercodeorder";
    String usercodeorder_key ="";

    Bitmap bitmap,scaleBitmap;
    int pageWidth = 1200;

    Button btnCreatePDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        getUsernameLocal();

        detailName = findViewById(R.id.detail_order_name);
        detailPackageClass = findViewById(R.id.detail_order_class);
        detailLocation = findViewById(R.id.detail_order_location);
        detailDate = findViewById(R.id.detail_order_date);
        detailPrice = findViewById(R.id.detail_order_price);

        btnCreatePDF = findViewById(R.id.btn_cetakpdf);

        databaseReference = FirebaseDatabase.getInstance("https://projectpuncak-2e271-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(usercodeorder_key);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                detailName.setText(snapshot.child("fullname").getValue().toString());
                detailPackageClass.setText(snapshot.child("classPackage").getValue().toString());
                detailLocation.setText(snapshot.child("location").getValue().toString());
                detailDate.setText(snapshot.child("date").getValue().toString());
                detailPrice.setText(snapshot.child("price").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //cover header
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        scaleBitmap = Bitmap.createScaledBitmap(bitmap, 1200, 518, false);

        //permission
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        printInvoice();

    }

    public void printInvoice() {
        btnCreatePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dateTime = new Date();

                PdfDocument pdfDocument = new PdfDocument();
                Paint paint = new Paint();
                Paint paint1 = new Paint();
                Paint titlePaint = new Paint();

                PdfDocument.PageInfo pageInfo
                        = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                PdfDocument.Page page = pdfDocument.startPage(pageInfo);

                Canvas canvas = page.getCanvas();
                canvas.drawBitmap(scaleBitmap, 0, 0, paint);

                paint.setColor(Color.WHITE);
                paint.setTextSize(30f);
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("DYO NUFOTO", 1160, 40, paint);
                canvas.drawText("Contact : 08123456789", 1160, 80, paint);

//                titlePaint.setTextAlign(Paint.Align.CENTER);
//                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
//                titlePaint.setColor(Color.WHITE);
//                titlePaint.setTextSize(70);
//                canvas.drawText("Tagihan Anda", pageWidth / 2, 500, titlePaint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.BLACK);
                paint.setTextSize(35f);
//                canvas.drawText("Nama Pemesan : " + detailName.getText().toString(), 20, 640, paint);
//                canvas.drawText("Nomor Tlp: " + detail.getText(), 20, 640, paint);

                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("No. Pesanan: " + usercodeorder_key,  20, 580, paint);

                dateFormat = new SimpleDateFormat("dd/MM/yy");
                canvas.drawText("Tanggal: " + dateFormat.format(dateTime),  20, 640, paint);

                dateFormat = new SimpleDateFormat("HH:mm:ss");
                canvas.drawText("Pukul: " + dateFormat.format(dateTime),  20, 700, paint);

                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(5);
                canvas.drawRect(20, 780, pageWidth - 20, 1460, paint);

                paint1.setColor(Color.BLACK);
                paint1.setTextSize(45f);

                paint1.setTextAlign(Paint.Align.LEFT);
                paint1.setStyle(Paint.Style.FILL);
                canvas.drawText("Nama :", 80, 860, paint1);
                canvas.drawText(detailName.getText().toString(), 580, 860, paint1);

                canvas.drawText("No.Telpon :", 80, 940, paint1);
                canvas.drawText("+6281234898979", 580, 940, paint1);

                canvas.drawText("Package :", 80, 1020, paint1);
                canvas.drawText("Wedding", 580, 1020, paint1);

                canvas.drawText("Kelas :", 80, 1100, paint1);
                canvas.drawText(detailPackageClass.getText().toString(), 580, 1100, paint1);

                canvas.drawText("Lokasi :", 80, 1180, paint1);
                canvas.drawText(detailLocation.getText().toString(), 580, 1180, paint1);

                canvas.drawText("Metode Pembayaran :", 80, 1260, paint1);
                canvas.drawText("BCA Virtual Account", 580, 1260, paint1);
//

                canvas.drawLine(400, 1700, pageWidth - 20, 1700, paint);
                canvas.drawText("Sub Total", 700, 1750, paint);
                canvas.drawText(":", 900, 1750, paint);
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(detailPrice.getText().toString(), pageWidth - 40, 1750, paint);

                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("PPN (10%)", 700, 1800, paint);
                canvas.drawText(":", 900, 1800, paint);
                paint.setTextAlign(Paint.Align.RIGHT);
//                canvas.drawText(String.valueOf(subTotal * 10 / 100), pageWidth - 40, 1300, paint);
                paint.setTextAlign(Paint.Align.LEFT);

                paint.setColor(Color.rgb(247, 147, 30));
                canvas.drawRect(680, 1850, pageWidth - 20, 1950, paint);

                paint.setColor(Color.BLACK);
                paint.setTextSize(50f);
                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("Total", 700, 1915, paint);
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(String.valueOf(detailPrice.getText().toString()), pageWidth - 40, 1915, paint);


                pdfDocument.finishPage(page);

                File file = new File(Environment.getExternalStorageDirectory(), "/Invoice DioPhoto.pdf");
                try {
                    pdfDocument.writeTo(new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pdfDocument.close();
                Toast.makeText(getApplicationContext(), "PDF sudah dibuat", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERCODEORDER,MODE_PRIVATE);
        usercodeorder_key = sharedPreferences.getString(usercodeorder_key,"");
    }
}