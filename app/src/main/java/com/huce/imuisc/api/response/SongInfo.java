package com.huce.imuisc.api.response;

import com.google.gson.annotations.SerializedName;
import com.huce.imuisc.model.Song;

public class SongInfo extends BaseResponse{
    @SerializedName("data")
    private Song data;

    public SongInfo(Song data) {
        this.data = data;
    }

    public Song getData() {
        return data;
    }

    public void setData(Song data) {
        this.data = data;
    }

    public String getTitle() {
        return data.getTitle();
    }

    public String getThumbnail() {
        return data.getThumbnail();
    }

    public String getThumbnailM() {
        return data.getThumbnailM();
    }

    public String getArtistsNames() {
        return data.getArtistsNames();
    }

    public String getEncodeId() {
        return data.getId();
    }
}