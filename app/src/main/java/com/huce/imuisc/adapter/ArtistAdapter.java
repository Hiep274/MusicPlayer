package com.huce.imuisc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huce.imuisc.R;
import com.huce.imuisc.model.Artist;
import com.huce.imuisc.view.ListSongActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {
    Context context;
    ArrayList<Artist> artists;
    View view;

    public ArtistAdapter(Context context, ArrayList<Artist> artists) {
        this.context = context;
        this.artists = artists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.artist,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Artist artist = artists.get(position);
        holder.txttennghesi.setText(artist.getName());
        Picasso.get(/*context*/).load(artist.getThumbnail()).into(holder.imgnghesi);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListSongActivity.class);
                intent.putExtra("id", artist.getPlayListId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return artists != null ? artists.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView imgnghesi;
        TextView txttennghesi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgnghesi = itemView.findViewById(R.id.imageviewnghesi);
            txttennghesi = itemView.findViewById(R.id.textviewnghesi);
        }
    }
}
