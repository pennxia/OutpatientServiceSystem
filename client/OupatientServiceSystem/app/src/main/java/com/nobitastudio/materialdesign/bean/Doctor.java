package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Doctor implements Serializable{

    @SerializedName("doctorId")
    private Integer doctorId;

    @SerializedName("doctorName")
    private String doctorName;

    @SerializedName("specialty")
    private String specialty;

    @SerializedName("submajor")
    private String submajor;

    @SerializedName("introduction")
    private String introduction;

    @SerializedName("doctorLevel")
    private String doctorLevel;

    @SerializedName("departmentNo")
    private Integer departmentNo;

    public Doctor() {
    }

    public Doctor(Integer doctorId, String doctorName, String specialty, String submajor, String introduction, String doctorLevel, Integer departmentNo) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.specialty = specialty;
        this.submajor = submajor;
        this.introduction = introduction;
        this.doctorLevel = doctorLevel;
        this.departmentNo = departmentNo;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getSubmajor() {
        return submajor;
    }

    public void setSubmajor(String submajor) {
        this.submajor = submajor;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDoctorLevel() {
        return doctorLevel;
    }

    public void setDoctorLevel(String doctorLevel) {
        this.doctorLevel = doctorLevel;
    }

    public Integer getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(Integer departmentNo) {
        this.departmentNo = departmentNo;
    }



}
