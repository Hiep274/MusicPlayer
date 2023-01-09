package com.huce.imuisc.api.response;

import com.google.gson.annotations.SerializedName;

public class Streaming {
    @SerializedName("data")
    private DataStreaming data;

    public Streaming(DataStreaming data) {
        this.data = data;
    }

    public DataStreaming getData() {
        return data;
    }

    public void setData(DataStreaming data) {
        this.data = data;
    }
    public String get_128() {
        return data.get_128();
    }

    public String get_320() {
        return data.get_320();
    }
}

class DataStreaming {
    @SerializedName("128")
    private String _128;

    @SerializedName("320")
    private String _320;

    public DataStreaming(String _128, String _320) {
        this._128 = _128;
        this._320 = _320;
    }

    public String get_128() {
        return _128;
    }

    public void set_128(String _128) {
        this._128 = _128;
    }

    public String get_320() {
        return _320;
    }

    public void set_320(String _320) {
        this._320 = _320;
    }
}