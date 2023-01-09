package com.huce.imuisc.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.LogPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.huce.imuisc.R;
import com.huce.imuisc.model.Playlist;
import com.huce.imuisc.model.PlaylistBest;
//import com.huce.imuisc.view.ListSongActivity;
import com.huce.imuisc.view.ListSongActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class PlaylistHomeAdapter extends RecyclerView.Adapter<PlaylistHomeAdapter.ViewHolder>{

    Context context;
    ArrayList<PlaylistBest> mangplaylist;
    View view;


    public PlaylistHomeAdapter(Context context, ArrayList<PlaylistBest> mangplaylist) {
        this.mangplaylist = mangplaylist;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaylistHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.playlist,parent, false);
        return new PlaylistHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistHomeAdapter.ViewHolder holder, final int position) {
        PlaylistBest playlist = mangplaylist.get(position);
        holder.txttenplaylist.setText(playlist.getTitle());
        Picasso.get().load(playlist.getThumbnail()).into(holder.imgplaylist);
        String id = playlist.getId();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListSongActivity.class);
                intent.putExtra("id", id);
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
