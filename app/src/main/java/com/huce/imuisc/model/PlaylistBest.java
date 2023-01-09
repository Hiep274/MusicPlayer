package com.huce.imuisc.model;

import com.google.gson.annotations.SerializedName;

public class PlaylistBest {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("artistsNames")
    private String artistsNames;


    public PlaylistBest(String id, String title, String thumbnail, String artistsNames) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.artistsNames = artistsNames;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getArtistsNames() {
        return artistsNames;
    }

    public void setArtistsNames(String artistsNames) {
        this.artistsNames = artistsNames;
    }
}