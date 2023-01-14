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
import com.huce.imuisc.adapter.SongAdapter;
import com.huce.imuisc.api.ApiService;
import com.huce.imuisc.api.response.HomeNewRelease;
import com.huce.imuisc.api.response.PlaylistHome;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTopic extends Fragment {

    View view;
    RecyclerView recyclerViewTopic;
    TextView nameTopic;
    SongAdapter playlistAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_topic, container, false);
        recyclerViewTopic = view.findViewById(R.id.recyclerviewchude);
        nameTopic = view.findViewById(R.id.txtchude);
        GetData();
        return view;
    }

    private void GetData() {
        ApiService.apiService.getHomeNewRelease().enqueue(new Callback<HomeNewRelease>() {
            @Override
            public void onResponse(@NonNull Call<HomeNewRelease> call, @NonNull Response<HomeNewRelease> response) {
                if (response.isSuccessful()) {
                    HomeNewRelease newRelease = response.body();
                    if (newRelease != null) {
                        nameTopic.setText(newRelease.getTitle());
                        playlistAdapter = new SongAdapter(getContext(), newRelease.getItems());
                        recyclerViewTopic.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        recyclerViewTopic.setAdapter(playlistAdapter);
                    } else {
                        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<HomeNewRelease> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error topic : ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
