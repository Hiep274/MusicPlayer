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
import com.huce.imuisc.adapter.HotAdapter;
import com.huce.imuisc.adapter.PlaylistHomeAdapter;
import com.huce.imuisc.api.ApiService;
import com.huce.imuisc.api.response.PlaylistHome;
import com.huce.imuisc.model.Song;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;


import java.util.ArrayList;
import java.util.List;



public class FragmentHot extends Fragment {

    View view;
    RecyclerView recyclerViewHot;
    TextView nameHot;
    PlaylistHomeAdapter hotAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hot, container, false);
        recyclerViewHot = view.findViewById(R.id.recyclerviewphobien);
        nameHot = view.findViewById(R.id.txtphobien);
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
                        nameHot.setText(newRelease.get(4).getTitle());
                        hotAdapter = new PlaylistHomeAdapter(getContext(), newRelease.get(4).getItems());
                        recyclerViewHot.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        recyclerViewHot.setAdapter(hotAdapter);
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