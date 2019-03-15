package com.sandy.guoguo.babylib.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/6/6.
 */

public class MdlBaseHttpResp<T> {
    @SerializedName("resCode")
    public int code;
    @SerializedName("resMessage")
    public String message;
    public int totalItems;

    @SerializedName("resBody")
    public T data;


    @Override
    public String toString() {
        return "MdlBaseHttpResp{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", totalItems=" + totalItems +
                ", data=" + data +
                '}';
    }
}
