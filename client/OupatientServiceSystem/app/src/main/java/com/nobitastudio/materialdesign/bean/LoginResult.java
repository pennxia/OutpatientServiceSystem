package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LoginResult implements Serializable{

    @SerializedName("uuser")
    private User user;

    @SerializedName("hospitalActivityList")
    private List<HospitalActivity> hospitalActivityList;

    @SerializedName("healthNewsList")
    private List<HealthNews> healthNewsList;

    @SerializedName("simpleHealthNewsList")
    private List<SimpleHealthNews> simpleHealthNewsList;

    public LoginResult() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<HospitalActivity> getHospitalActivityList() {
        return hospitalActivityList;
    }

    public void setHospitalActivityList(List<HospitalActivity> hospitalActivityList) {
        this.hospitalActivityList = hospitalActivityList;
    }

    public List<HealthNews> getHealthNewsList() {
        return healthNewsList;
    }

    public void setHealthNewsList(List<HealthNews> healthNewsList) {
        this.healthNewsList = healthNewsList;
    }

    public List<SimpleHealthNews> getSimpleHealthNewsList() {
        return simpleHealthNewsList;
    }

    public void setSimpleHealthNewsList(List<SimpleHealthNews> simpleHealthNewsList) {
        this.simpleHealthNewsList = simpleHealthNewsList;
    }
}
