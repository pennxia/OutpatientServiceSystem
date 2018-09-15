package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DrugCost implements Serializable {

    @SerializedName("drugCostId")
    private String drugCostId;

    @SerializedName("drugListId")
    private String drugListId;

    @SerializedName("allPrice")
    private Double allPrice;

    @SerializedName("medicalCardNo")
    private  Long medicalCardNo;

    @SerializedName("createTime")
    private String createTime;

    @SerializedName("account")
    private Long account;

    @SerializedName("doctorNoteId")
    private String doctorNoteId;

    public DrugCost() {
    }

    public String getDrugCostId() {
        return drugCostId;
    }

    public void setDrugCostId(String drugCostId) {
        this.drugCostId = drugCostId;
    }

    public String getDrugListId() {
        return drugListId;
    }

    public void setDrugListId(String drugListId) {
        this.drugListId = drugListId;
    }

    public Double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(Double allPrice) {
        this.allPrice = allPrice;
    }

    public Long getMedicalCardNo() {
        return medicalCardNo;
    }

    public void setMedicalCardNo(Long medicalCardNo) {
        this.medicalCardNo = medicalCardNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public String getDoctorNoteId() {
        return doctorNoteId;
    }

    public void setDoctorNoteId(String doctorNoteId) {
        this.doctorNoteId = doctorNoteId;
    }
}
