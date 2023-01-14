package com.huce.imuisc.api.response;

import com.google.gson.annotations.SerializedName;
import com.huce.imuisc.model.Song;

import java.util.ArrayList;

public class Recomend extends BaseResponse {
    @SerializedName("data")
    private ArrayList<Song> data;

    public ArrayList<Song> getData() {
        return data;
    }

    public void setData(ArrayList<Song> data) {
        this.data = data;
    }
}


