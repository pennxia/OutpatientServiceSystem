package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OutpatientDepartment implements Serializable {

    @SerializedName("departmentNo")
    private int outpatientDepartmentId;

    @SerializedName("departmentName")
    private String outpatientDepartmentName;

    @SerializedName("departmentAddress")
    private String departmentAddress;

    public OutpatientDepartment() {
    }

    public int getOutpatientDepartmentId() {
        return outpatientDepartmentId;
    }

    public void setOutpatientDepartmentId(int outpatientDepartmentId) {
        this.outpatientDepartmentId = outpatientDepartmentId;
    }

    public String getOutpatientDepartmentName() {
        return outpatientDepartmentName;
    }

    public void setOutpatientDepartmentName(String outpatientDepartmentName) {
        this.outpatientDepartmentName = outpatientDepartmentName;
    }

    public String getDepartmentAddress() {
        return departmentAddress;
    }

    public void setDepartmentAddress(String departmentAddress) {
        this.departmentAddress = departmentAddress;
    }
}
