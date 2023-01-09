package com.huce.imuisc.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huce.imuisc.R;
import com.huce.imuisc.adapter.PlaylistHomeAdapter;
import com.huce.imuisc.api.ApiService;
import com.huce.imuisc.api.response.PlaylistHome;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTheBest extends Fragment {
    View view;
    RecyclerView recyclerViewthebest;
    TextView nameTheBest;
    PlaylistHomeAdapter playlistAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_the_best, container, false);
        recyclerViewthebest = view.findViewById(R.id.recyclerviewthebest);
        nameTheBest = view.findViewById(R.id.txtthebest);
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
                        nameTheBest.setText(newRelease.get(0).getTitle());
                        playlistAdapter = new PlaylistHomeAdapter(getContext(), newRelease.get(0).getItems());
                        recyclerViewthebest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        recyclerViewthebest.setAdapter(playlistAdapter);
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