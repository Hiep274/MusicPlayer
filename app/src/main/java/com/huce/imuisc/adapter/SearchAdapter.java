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

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    Context context;
    ArrayList<SongList> mangbaihat;

    public SearchAdapter(Context context, ArrayList<SongList> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongList baiHat = mangbaihat.get(position);
        holder.txttentimkiem.setText(baiHat.getTitle());
        holder.txtcasitimkiem.setText(baiHat.getArtistsNames());
        Picasso.get().load(baiHat.getThumbnail()).into(holder.imganhtimkiem);
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

        TextView txttentimkiem, txtcasitimkiem;
        CircleImageView imganhtimkiem;
        ConstraintLayout cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttentimkiem = itemView.findViewById(R.id.txttennhac);
            txtcasitimkiem = itemView.findViewById(R.id.txtcasinhac);
            imganhtimkiem = itemView.findViewById(R.id.imgnhac);
            cardView = itemView.findViewById(R.id.fragment_search);
        }
    }

}
