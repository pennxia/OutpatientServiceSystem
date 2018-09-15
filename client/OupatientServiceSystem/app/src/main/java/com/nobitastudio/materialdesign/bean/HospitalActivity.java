package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HospitalActivity implements Serializable{

    @SerializedName("hospitalActivityId")
    private String hospitalActivityId;

    @SerializedName("hospitalActivityIconName")
    private String hospitalActivityIconName;

    @SerializedName("url")
    private String url;

    public HospitalActivity() {
    }

    public String getHospitalActivityId() {
        return hospitalActivityId;
    }

    public void setHospitalActivityId(String hospitalActivityId) {
        this.hospitalActivityId = hospitalActivityId;
    }

    public String getHospitalActivityIconName() {
        return hospitalActivityIconName;
    }

    public void setHospitalActivityIconName(String hospitalActivityIconName) {
        this.hospitalActivityIconName = hospitalActivityIconName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
