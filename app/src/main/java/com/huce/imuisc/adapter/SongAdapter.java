package com.huce.imuisc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.huce.imuisc.R;
import com.huce.imuisc.model.Song;
import com.huce.imuisc.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>{

    Context context;
    ArrayList<Song> mangplaylist;
    View view;


    public SongAdapter(Context context, ArrayList<Song> mangplaylist) {
        this.mangplaylist = mangplaylist;
        this.context = context;
    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.playlist,parent, false);
        return new SongAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, final int position) {
        Song playlist = mangplaylist.get(position);
        holder.txttenplaylist.setText(playlist.getTitle());
        Picasso.get().load(playlist.getThumbnail()).into(holder.imgplaylist);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("id", playlist.getId());
                intent.putExtra("check", "1");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mangplaylist != null ? mangplaylist.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgplaylist;
        TextView txttenplaylist;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgplaylist = itemView.findViewById(R.id.imageviewplaylist);
            txttenplaylist = itemView.findViewById(R.id.textviewplaylist);
            cardView = itemView.findViewById(R.id.itemplaylist);

        }
    }
}
