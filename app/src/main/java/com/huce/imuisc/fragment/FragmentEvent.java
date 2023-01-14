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

public class FragmentEvent extends Fragment {
    View view;
    RecyclerView recyclerViewEvent;
    TextView nameEvent;
    PlaylistHomeAdapter playlistAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event, container, false);
        recyclerViewEvent = view.findViewById(R.id.recyclerviewevent);
        nameEvent = view.findViewById(R.id.txtevent);
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
                        nameEvent.setText(newRelease.get(1).getTitle());
                        playlistAdapter = new PlaylistHomeAdapter(getContext(), newRelease.get(1).getItems());
                        recyclerViewEvent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        recyclerViewEvent.setAdapter(playlistAdapter);
                    } else {
                        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<PlaylistHome>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error event : ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}