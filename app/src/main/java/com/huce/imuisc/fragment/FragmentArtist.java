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
import com.huce.imuisc.adapter.ArtistAdapter;
import com.huce.imuisc.api.ApiService;
import com.huce.imuisc.api.response.ArtistHome;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentArtist extends Fragment {

    View view;
    RecyclerView recyclerViewArtist;
    TextView tenNgheSi;
    ArtistAdapter artistAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_artist, container, false);
        recyclerViewArtist = view.findViewById(R.id.recyclerviewnghesi);
        tenNgheSi = view.findViewById(R.id.txtnghesi);
        GetData();
        return view;
    }

    private void GetData() {
        ApiService.apiService.getHomeArtist().enqueue(new Callback<ArtistHome>() {
            @Override
            public void onResponse(@NonNull Call<ArtistHome> call, @NonNull Response<ArtistHome> response) {
                if (response.isSuccessful()) {
                    ArtistHome newRelease = response.body();
                    if (newRelease != null) {
                        artistAdapter = new ArtistAdapter(getContext(), newRelease.getItems());
                        tenNgheSi.setText(newRelease.getTitle());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerViewArtist.setLayoutManager(linearLayoutManager);
                        recyclerViewArtist.setAdapter(artistAdapter);
                    } else {
                        Toast.makeText(getContext(), "Lỗi t", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<ArtistHome> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}