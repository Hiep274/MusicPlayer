package com.huce.imuisc.api.response;

import com.google.gson.annotations.SerializedName;
import com.huce.imuisc.model.PlaylistBest;
import com.huce.imuisc.model.Song;

import java.util.ArrayList;

public class HomeNewRelease extends BaseResponse {
    @SerializedName("title")
    private String title;

    @SerializedName("items")
    private ArrayList<Song> items;

    public HomeNewRelease(String title, ArrayList<Song> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Song> getItems() {
        return items;
    }

    public void setItems(ArrayList<Song> items) {
        this.items = items;
    }




}

