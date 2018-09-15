package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegistrationForm implements Serializable {

    @SerializedName("outpatientName")
    private String outpatientName;

    @SerializedName("doctorName")
    private String doctorName;

    @SerializedName("medicalCardOwnerName")
    private String medicalCardOwnerName;

    @SerializedName("yearMonthDate")
    private YearMonthDate yearMonthDate;

    @SerializedName("timeSlot")
    private String timeSlot;

    @SerializedName("diagnosisNo")
    private String diagnosisNo;

    @SerializedName("cost")
    private String cost;

    @SerializedName("address")
    private String address;

    @SerializedName("appointSource")
    private String appointSource;

    public RegistrationForm() {
    }

    public String getOutpatientName() {
        return outpatientName;
    }

    public void setOutpatientName(String outpatientName) {
        this.outpatientName = outpatientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMedicalCardOwnerName() {
        return medicalCardOwnerName;
    }

    public void setMedicalCardOwnerName(String medicalCardOwnerName) {
        this.medicalCardOwnerName = medicalCardOwnerName;
    }

    public YearMonthDate getYearMonthDate() {
        return yearMonthDate;
    }

    public void setYearMonthDate(YearMonthDate yearMonthDate) {
        this.yearMonthDate = yearMonthDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getDiagnosisNo() {
        return diagnosisNo;
    }

    public void setDiagnosisNo(String diagnosisNo) {
        this.diagnosisNo = diagnosisNo;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppointSource() {
        return appointSource;
    }

    public void setAppointSource(String appointSource) {
        this.appointSource = appointSource;
    }


}
