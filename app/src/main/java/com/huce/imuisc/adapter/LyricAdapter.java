package com.huce.imuisc.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.huce.imuisc.R;
import com.huce.imuisc.model.Lyric;


import java.util.ArrayList;

public class LyricAdapter extends RecyclerView.Adapter<LyricAdapter.ViewHolder>{

    Context context;
    ArrayList<Lyric> lyrics;

    public LyricAdapter(Context context, ArrayList<Lyric> lyrics) {
        this.context = context;
        this.lyrics = lyrics;
    }

    @NonNull
    @Override
    public LyricAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lyric, parent, false);
        return new LyricAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LyricAdapter.ViewHolder holder, int position) {
        Lyric baiHat = lyrics.get(position);
        holder.textviewlyric.setText(baiHat.getWords());

    }

    @Override
    public int getItemCount() {
        return lyrics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textviewlyric;
        ConstraintLayout cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textviewlyric = itemView.findViewById(R.id.textviewlyric);
        }
    }
}
