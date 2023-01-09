package com.huce.imuisc.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huce.imuisc.R;
import com.huce.imuisc.adapter.PlaylistHomeAdapter;
import com.huce.imuisc.api.ApiService;
import com.huce.imuisc.api.response.PlaylistHome;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPlaylist extends Fragment {
    View view;
    RecyclerView recyclerViewPlaylist;
    TextView namePlaylist;
    PlaylistHomeAdapter playlistAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlist, container, false);
        recyclerViewPlaylist = view.findViewById(R.id.recyclerviewplaylist);
        namePlaylist = view.findViewById(R.id.txtplaylist);
        GetData();
        return view;
    }



    private void GetData() {
        ApiService.apiService.getHomePlaylist().enqueue(new Callback<ArrayList<PlaylistHome>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<PlaylistHome>> call, @NonNull Response<ArrayList<PlaylistHome>> response) {
                if (response.isSuccessful()) {
                    ArrayList<PlaylistHome> newRelease = response.body();
                    if (newRelease != null) {
                        namePlaylist.setText(newRelease.get(3).getTitle());
                        playlistAdapter = new PlaylistHomeAdapter(getContext(), newRelease.get(3).getItems());
                        recyclerViewPlaylist.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        recyclerViewPlaylist.setAdapter(playlistAdapter);
                    } else {
                        Toast.makeText(getContext(), "Lỗi tr", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<PlaylistHome>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });

    }
}