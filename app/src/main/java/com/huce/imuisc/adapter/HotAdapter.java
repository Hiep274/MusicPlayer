package com.huce.imuisc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huce.imuisc.R;
import com.huce.imuisc.model.Song;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.ViewHolder> {

    Context context;
    ArrayList<Song> arrayListphobien;
    View view;

    public HotAdapter(Context context, ArrayList<Song> arrayListphobien) {
        this.context = context;
        this.arrayListphobien = arrayListphobien;
    }

    @NonNull
    @Override
    public HotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.hot, parent, false);
        return new HotAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotAdapter.ViewHolder holder, final int position) {
        Song hot = arrayListphobien.get(position);
        holder.txttenphobien.setText(hot.getTitle());
        Picasso.get(/*context*/).load(hot.getThumbnail()).into(holder.imgphobien);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListphobien != null ? arrayListphobien.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgphobien;
        TextView txttenphobien;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgphobien = itemView.findViewById(R.id.imageviewphobien);
            txttenphobien = itemView.findViewById(R.id.textviewphobien);
        }
    }
}