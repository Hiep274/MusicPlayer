package com.huce.imuisc.model;

import com.google.gson.annotations.SerializedName;

public class Artist {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("alias")
    private String alias;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("playlistId")
    private String playListId;

    public Artist(String id, String name, String alias, String thumbnail, String playListId) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.thumbnail = thumbnail;
        this.playListId = playListId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getPlayListId() {
        return playListId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setPlayListId(String playListId) {
        this.playListId = playListId;
    }

}
