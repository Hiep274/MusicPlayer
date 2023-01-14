package com.huce.imuisc.model;

import com.google.gson.annotations.SerializedName;

public class Lyric {
    @SerializedName("words")
    private String words;

    public Lyric(String words) {
        this.words = words;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }


}
