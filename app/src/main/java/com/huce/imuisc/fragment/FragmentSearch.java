package com.huce.imuisc.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huce.imuisc.R;
import com.huce.imuisc.adapter.ArtistAdapter;
import com.huce.imuisc.adapter.PlaylistHomeAdapter;
import com.huce.imuisc.adapter.SearchAdapter;
import com.huce.imuisc.api.ApiService;
import com.huce.imuisc.api.response.SearchMulti;
import com.huce.imuisc.model.Artist;
import com.huce.imuisc.model.PlaylistBest;
import com.huce.imuisc.model.SongList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSearch extends Fragment {

    View view;
    androidx.appcompat.widget.Toolbar toolbar;
    RecyclerView recyclerViewArtist, recyclerViewPlaylist, recyclerViewSong;
    TextView textViewnull,textArtist, textPlaylist, textSong;
    ImageView imageviewtimkiemnull;
    SearchAdapter timKiemAdapter;
    PlaylistHomeAdapter playlistHomeAdapter;
    ArtistAdapter artistAdapter;
    ArrayList<SongList> mangbaihat;
    ArrayList<PlaylistBest> mangplaylist;
    ArrayList<Artist> mangartist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        toolbar = view.findViewById(R.id.toilbartimkiem);
        recyclerViewArtist = view.findViewById(R.id.recyclerViewSearchArtist);
        recyclerViewPlaylist = view.findViewById(R.id.recyclerViewSearchPlaylist);
        recyclerViewSong = view.findViewById(R.id.recyclerViewSearchSong);
        textViewnull = view.findViewById(R.id.textviewtimkiemnull);
        imageviewtimkiemnull = view.findViewById(R.id.imageviewtimkiemnull);
        textViewnull.setVisibility(View.GONE);
        imageviewtimkiemnull.setVisibility(View.GONE);
        textArtist = view.findViewById(R.id.textArtist);
        textPlaylist = view.findViewById(R.id.textPlaylist);
        textSong = view.findViewById(R.id.textSong);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.menu_timkiem, menu);
        MenuItem menuItem = menu.findItem(R.id.menutimkiem);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (!s.trim().equals("")){
                    Search(s);
                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void Search(String query){
        ApiService.apiService.getHomeSearch(query).enqueue(new Callback<SearchMulti>() {
            @Override
            public void onResponse(Call<SearchMulti> call, Response<SearchMulti> response) {
                if (response.isSuccessful()){
                    SearchMulti searchMulti = response.body();
                    if (searchMulti != null){
                        textViewnull.setVisibility(View.GONE);
                        imageviewtimkiemnull.setVisibility(View.GONE);
                        mangbaihat = searchMulti.getSongs();
                        mangplaylist = searchMulti.getPlaylists();
                        mangartist = searchMulti.getArtists();

                        if (mangbaihat.size() > 0){
                            timKiemAdapter = new SearchAdapter(getActivity(), mangbaihat);
                            recyclerViewSong.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            recyclerViewSong.setAdapter(timKiemAdapter);
                            recyclerViewSong.setVisibility(View.VISIBLE);
                            textSong.setText("Bài hát");
                        }

                        if (mangplaylist.size() > 0){
                            playlistHomeAdapter = new PlaylistHomeAdapter(getActivity(), mangplaylist);
                            recyclerViewPlaylist.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                            recyclerViewPlaylist.setAdapter(playlistHomeAdapter);
                            recyclerViewPlaylist.setVisibility(View.VISIBLE);
                            textPlaylist.setText("Danh sách phát");
                        }

                        if (mangartist.size() > 0){
                            artistAdapter = new ArtistAdapter(getActivity(), mangartist);
                            recyclerViewArtist.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                            recyclerViewArtist.setAdapter(artistAdapter);
                            recyclerViewArtist.setVisibility(View.VISIBLE);
                            textArtist.setText("Nghệ sĩ");
                        }


                    }
                }else {
                    textViewnull.setVisibility(View.VISIBLE);
                    imageviewtimkiemnull.setVisibility(View.VISIBLE);
                    recyclerViewArtist.setVisibility(View.GONE);
                    recyclerViewPlaylist.setVisibility(View.GONE);
                    recyclerViewSong.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<SearchMulti> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
