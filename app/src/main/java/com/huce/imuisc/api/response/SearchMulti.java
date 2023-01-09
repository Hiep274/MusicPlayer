package com.huce.imuisc.api.response;

import com.google.gson.annotations.SerializedName;
import com.huce.imuisc.model.Artist;
import com.huce.imuisc.model.PlaylistBest;
import com.huce.imuisc.model.Song;
import com.huce.imuisc.model.SongList;

import java.util.ArrayList;
import java.util.List;

public class SearchMulti extends BaseResponse {
    @SerializedName("artists")
    private ArrayList<Artist> artists;

    @SerializedName("playlists")
    private ArrayList<PlaylistBest> playlists;

    @SerializedName("songs")
    private ArrayList<SongList> songs;

    public SearchMulti( ArrayList<Artist> artists, ArrayList<PlaylistBest> playlists, ArrayList<SongList> songs) {
        this.artists = artists;
        this.playlists = playlists;
        this.songs = songs;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public ArrayList<PlaylistBest> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<PlaylistBest> playlists) {
        this.playlists = playlists;
    }

    public ArrayList<SongList> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<SongList> songs) {
        this.songs = songs;
    }








}


