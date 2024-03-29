package com.huce.imuisc.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huce.imuisc.R;
import com.huce.imuisc.adapter.ListSongAdapter;
import com.huce.imuisc.api.ApiService;
import com.huce.imuisc.api.response.ListSong;
import com.huce.imuisc.model.SongList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSongActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    RecyclerView recyclerViewdanhsachbaihat;
    TextView txtcollapsing;
    ArrayList<SongList> mangbaihat;
    ListSongAdapter danhsachbaihatAdapter;
    ImageView imageviewdanhsachcakhuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);
        toolbar = findViewById(R.id.toolbardanhsachbaihat);
        recyclerViewdanhsachbaihat = findViewById(R.id.recyclerviewdanhsachbaihat);
        imageviewdanhsachcakhuc = findViewById(R.id.imageviewdanhsachcakhuc);
        txtcollapsing = findViewById(R.id.textViewcollapsing);
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
            }
        }, 3500);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(v -> finish());
        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");
        GetDataPlaylist(id);
    }
    private void GetDataPlaylist(String id) {
        ApiService.apiService.getPlaylist(id).enqueue(new Callback<ListSong>() {
            @Override
            public void onResponse(Call<ListSong> call, Response<ListSong> response) {
                if(response.isSuccessful()){
                    ListSong playlist = response.body();
                    Picasso.get().load(playlist.getThumbnail()).into(imageviewdanhsachcakhuc);
                    txtcollapsing.setText(playlist.getTitle());
                    mangbaihat = playlist.getItems();
                    danhsachbaihatAdapter = new ListSongAdapter(ListSongActivity.this, mangbaihat);
                    recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(ListSongActivity.this));
                    recyclerViewdanhsachbaihat.setAdapter(danhsachbaihatAdapter);
                }else{
                    Toast.makeText(ListSongActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ListSong> call, Throwable t) {
                Toast.makeText(ListSongActivity.this, "Error list song : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}