package com.example.testbarang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterLihatBarang extends RecyclerView.Adapter<AdapterLihatBarang.ViewHolder> {
    private ArrayList<Barang> daftarBarang;
    private Context context;

    public AdapterLihatBarang(ArrayList<Barang> barangs, Context ctx) {
        daftarBarang = barangs;
        context = ctx;
        listener = (LihatBarang) ctx;
    }

    public interface dataListener {
        void onDeleteData(Barang barang);
    }

    dataListener listener;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String name = daftarBarang.get(position).getNama();
        holder.listItemBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = "Nama Barang     : " + daftarBarang.get(position).getNama();
                String kode = "Kode Barang      : " + daftarBarang.get(position).getKode();

                final String[] detailItem = {kode, nama};

                AlertDialog.Builder builderAlert = new AlertDialog.Builder(v.getContext());
                builderAlert.setTitle("Detail").setItems(detailItem, null);
                builderAlert.create();
                builderAlert.show();
            }
        });

        holder.listItemBarang.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                AlertDialog.Builder aleBuilder = new AlertDialog.Builder(v.getContext());

                final String[] items = {"Update", "Delete"};

                aleBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Bundle bundle = new Bundle();
                                bundle.putString("kode", daftarBarang.get(position).getKode());
                                bundle.putString("nama", daftarBarang.get(position).getNama());
                                bundle.putString("key", daftarBarang.get(position).getKey());

                                Intent intent = new Intent(v.getContext(), UpdateData.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);

                                break;
                            case 1:
                                listener.onDeleteData(daftarBarang.get(position));
                                break;

                        }
                    }
                });
                aleBuilder.create();
                aleBuilder.show();

                return true;
            }
        });
        holder.tvTitle.setText(name);
    }

    @Override
    public int getItemCount() {
        return daftarBarang.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        LinearLayout listItemBarang;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_namabarang);
            listItemBarang = (LinearLayout) itemView.findViewById(R.id.list_itemBarang);

        }
    }


}
