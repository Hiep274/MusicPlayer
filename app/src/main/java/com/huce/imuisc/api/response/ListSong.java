package com.huce.imuisc.api.response;

import com.google.gson.annotations.SerializedName;
import com.huce.imuisc.model.Song;
import com.huce.imuisc.model.SongList;

import java.util.ArrayList;
import java.util.List;

public class ListSong extends BaseResponse {
    @SerializedName("title")
    private String title;

    @SerializedName("items")
    private ArrayList<SongList> items;

    @SerializedName("thumbnail")
    private String thumbnail;

    public ListSong(String title, ArrayList<SongList> items, String thumbnail) {
        this.title = title;
        this.items = items;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<SongList> getItems() {
        return items;
    }

    public void setItems(ArrayList<SongList> items) {
        this.items = items;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


}
