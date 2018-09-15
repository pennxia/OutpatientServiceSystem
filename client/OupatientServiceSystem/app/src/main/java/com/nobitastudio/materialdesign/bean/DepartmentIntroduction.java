package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DepartmentIntroduction implements Serializable {

    @SerializedName("departmentNo")
    private Integer departmentNo;

    @SerializedName("location")
    private String location;

    @SerializedName("floor")
    private String floor;

    @SerializedName("area")
    private String area;

    @SerializedName("introduction")
    private String introduction;

    public DepartmentIntroduction() {
    }

    public Integer getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(Integer departmentNo) {
        this.departmentNo = departmentNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
