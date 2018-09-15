package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Diagnosis implements Serializable{

    @SerializedName("diagnosisNo")
    private Integer diagnosisNo;

    @SerializedName("registrationNo")
    private String registrationNo;

    public Diagnosis() {
    }

    public Diagnosis(Integer diagnosisNo, String registrationNo) {
        this.diagnosisNo = diagnosisNo;
        this.registrationNo = registrationNo;
    }

    public Integer getDiagnosisNo() {
        return diagnosisNo;
    }

    public void setDiagnosisNo(Integer diagnosisNo) {
        this.diagnosisNo = diagnosisNo;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }
}
