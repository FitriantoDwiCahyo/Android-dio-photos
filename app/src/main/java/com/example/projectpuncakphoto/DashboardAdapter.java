package com.example.projectpuncakphoto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {


    Context context;

    ArrayList<DataOrder> list;

    public DashboardAdapter(Context context, ArrayList<DataOrder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.transaksi,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DataOrder dataOrder = list.get(position);
        holder.name.setText(dataOrder.getFullname());
        holder.paket.setText(dataOrder.getClassPackage());
        holder.harga.setText(dataOrder.getPrice());

        final String getDetailOrder = dataOrder.getFullname();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("fullname",dataOrder.getFullname());
                bundle.putString("classPackage",dataOrder.getClassPackage());
                bundle.putString("price",dataOrder.getPrice());
                bundle.putString("date",dataOrder.getDate());
                bundle.putString("location",dataOrder.getLocation());
                bundle.putString("getPrimaryKey",dataOrder.getUserCodeOrder());

                Intent detailOrder = new Intent(context,DetailOrderActivity.class);
                detailOrder.putExtras(bundle);
                context.startActivity(detailOrder);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,paket,harga;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_transaksi);
            paket = itemView.findViewById(R.id.paket_transaksi);
            harga = itemView.findViewById(R.id.harga_transaksi);
        }
    }

}
