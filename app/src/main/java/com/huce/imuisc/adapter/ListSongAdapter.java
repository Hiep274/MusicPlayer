package com.huce.imuisc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.huce.imuisc.R;
import com.huce.imuisc.model.SongList;
import com.huce.imuisc.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.ViewHolder>{

    Context context;
    ArrayList<SongList> mangbaihat;

    public ListSongAdapter(Context context, ArrayList<SongList> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongList baiHat = mangbaihat.get(position);
        holder.txttenbaihat.setText(baiHat.getTitle());
        holder.txttencasi.setText(baiHat.getArtistsNames());
        Picasso.get().load(baiHat.getThumbnail()).into(holder.hinhbaihat);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("id", baiHat.getId());
                intent.putExtra("check", "1");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txttenbaihat, txttencasi;
        ImageView hinhbaihat, tim;
        ConstraintLayout cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttenbaihat = itemView.findViewById(R.id.textViewtenbaihat);
            txttencasi = itemView.findViewById(R.id.textViewtencasi);
            hinhbaihat = itemView.findViewById(R.id.imageViewhinhbaihat);
            cardView = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
