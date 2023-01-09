package com.huce.imuisc.api.response;

import com.google.gson.annotations.SerializedName;
import com.huce.imuisc.model.Playlist;
import com.huce.imuisc.model.PlaylistBest;

import java.util.ArrayList;

public class PlaylistHome extends BaseResponse {
    @SerializedName("title")
    private String title;

    @SerializedName("items")
    private ArrayList<PlaylistBest> items;

    public PlaylistHome(String title, ArrayList<PlaylistBest> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<PlaylistBest> getItems() {
        return items;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setItems(ArrayList<PlaylistBest> items) {
        this.items = items;
    }

}
