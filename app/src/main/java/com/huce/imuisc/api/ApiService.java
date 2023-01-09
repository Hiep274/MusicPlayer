package com.huce.imuisc.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huce.imuisc.api.response.ArtistHome;
import com.huce.imuisc.api.response.Home;
import com.huce.imuisc.api.response.HomeNewRelease;
import com.huce.imuisc.api.response.ListSong;
import com.huce.imuisc.api.response.PlaylistHome;
import com.huce.imuisc.api.response.SearchMulti;
import com.huce.imuisc.api.response.SongInfo;
import com.huce.imuisc.api.response.Streaming;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://dev.thangvu.site/imusic/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("home")
    Call<Home> getHome();


    @GET("streaming/{id}")
    Call<Streaming> getStreaming(@Path("id") String id);

    @GET("search-multi")
    Call<SearchMulti> getSearchMulti(@Query("keyword") String keyword);

    @GET("home-artist")
    Call<ArtistHome> getHomeArtist();

    @GET("home-playlist")
    Call<ArrayList<PlaylistHome>> getHomePlaylist();

    @GET("home-new-release")
    Call<HomeNewRelease> getHomeNewRelease();

    @GET("home-playlist/{id}")
    Call<ListSong> getPlaylist(@Path("id") String id);

    @GET("home-search")
    Call<SearchMulti> getHomeSearch(@Query("keyword") String keyword);

    @GET("info-song/{id}")
    Call<SongInfo> getInfoSong(@Path("id") String id);

}
