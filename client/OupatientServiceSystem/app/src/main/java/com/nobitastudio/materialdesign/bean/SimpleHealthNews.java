package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SimpleHealthNews implements Serializable {

    @SerializedName("simpleHealthNewsId")
    private String simpleHealthNewsId;

    @SerializedName("simpleHealthNewsIconName")
    private String simpleHealthNewsIconName;

    @SerializedName("url")
    private String url;

    public SimpleHealthNews() {
    }

    public String getSimpleHealthNewsId() {
        return simpleHealthNewsId;
    }

    public void setSimpleHealthNewsId(String simpleHealthNewsId) {
        this.simpleHealthNewsId = simpleHealthNewsId;
    }

    public String getSimpleHealthNewsIconName() {
        return simpleHealthNewsIconName;
    }

    public void setSimpleHealthNewsIconName(String simpleHealthNewsIconName) {
        this.simpleHealthNewsIconName = simpleHealthNewsIconName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
