package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DoctorWhetherHasNumber implements Serializable {

    @SerializedName("doctorId")
    private Integer doctorId;

    @SerializedName("doctorhasNumber")
    private boolean doctorhasNumber;

    public DoctorWhetherHasNumber() {
    }

    public DoctorWhetherHasNumber(boolean doctorhasNumber) {
        this.doctorhasNumber = doctorhasNumber;
    }

    public DoctorWhetherHasNumber(Integer doctorId, boolean doctorhasNumber) {
        this.doctorId = doctorId;
        this.doctorhasNumber = doctorhasNumber;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public boolean isDoctorhasNumber() {
        return doctorhasNumber;
    }

    public void setDoctorhasNumber(boolean doctorhasNumber) {
        this.doctorhasNumber = doctorhasNumber;
    }
}
