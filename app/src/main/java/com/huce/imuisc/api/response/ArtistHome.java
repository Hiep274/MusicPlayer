package com.huce.imuisc.api.response;

import com.google.gson.annotations.SerializedName;
import com.huce.imuisc.model.Artist;

import java.util.ArrayList;

public class ArtistHome extends BaseResponse {
    @SerializedName("title")
    private String title;

    @SerializedName("items")
    private ArrayList<Artist> items;

    public ArtistHome(String title, ArrayList<Artist> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Artist> getItems() {
        return items;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setItems(ArrayList<Artist> items) {
        this.items = items;
    }


}


