package com.huce.imuisc.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Song {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("thumbnailM")
    private String thumbnailM;

    @SerializedName("artistsNames")
    private String artistsNames;


    public Song(String id, String title, String thumbnail,String thumbnailM, String artistsNames, int duration) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.thumbnailM = thumbnailM;
        this.artistsNames = artistsNames;
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

    public String getThumbnailM() {
        return thumbnailM;
    }

    public String getArtistsNames() {
        return artistsNames;
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

    public void setThumbnailM(String thumbnailM) {
        this.thumbnailM = thumbnailM;
    }

    public void setArtistsNames(String artistsNames) {
        this.artistsNames = artistsNames;
    }

}