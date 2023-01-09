package com.huce.imuisc.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SongList {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("artistsNames")
    private String artistsNames;

    @SerializedName("duration")
    private int duration;

    @SerializedName("artists")
    private List<Artist> artists;

    public SongList(String id, String title, String thumbnail, String artistsNames, int duration) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.artistsNames = artistsNames;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getArtistsNames() {
        return artistsNames;
    }

    public int getDuration() {
        return duration;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setArtistsNames(String artistsNames) {
        this.artistsNames = artistsNames;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
