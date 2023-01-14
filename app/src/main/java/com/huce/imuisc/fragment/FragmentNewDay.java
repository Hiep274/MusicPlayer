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

public class FragmentNewDay extends Fragment {
        View view;
        RecyclerView recyclerViewnewday;
        TextView nameNewDay;
        PlaylistHomeAdapter playlistAdapter;



        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_new_day, container, false);
            recyclerViewnewday = view.findViewById(R.id.recyclerviewnewday);
            nameNewDay = view.findViewById(R.id.txtnewday);
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
                            nameNewDay.setText(newRelease.get(2).getTitle());
                            playlistAdapter = new PlaylistHomeAdapter(getContext(), newRelease.get(2).getItems());
                            recyclerViewnewday.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                            recyclerViewnewday.setAdapter(playlistAdapter);
                        } else {
                            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ArrayList<PlaylistHome>> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Error in fgm newday : ", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }