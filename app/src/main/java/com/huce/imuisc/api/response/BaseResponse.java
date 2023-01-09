package com.huce.imuisc.api.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("err")
    int err;
    @SerializedName("msg")
    String msg;
    @SerializedName("timestamp")
    long timestamp;
}
