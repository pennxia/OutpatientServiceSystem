package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

public class ClientDoctor {

    @SerializedName("doctor")
    private Doctor doctor;

    @SerializedName("department")
    private OutpatientDepartment outpatientDepartment;

    @SerializedName("hospitalName")
    private String hospitalName;

    public ClientDoctor() {
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public OutpatientDepartment getOutpatientDepartment() {
        return outpatientDepartment;
    }

    public void setOutpatientDepartment(OutpatientDepartment outpatientDepartment) {
        this.outpatientDepartment = outpatientDepartment;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
