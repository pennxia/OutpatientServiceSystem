package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MedicalCard implements Serializable{

    @SerializedName("medicalCardNo")
    private Long medicalCardNo;

    @SerializedName("ownerAccount")
    private Long ownerAccount;

    @SerializedName("ownerIdCard")
    private String ownerIdCard;

    @SerializedName("ownerName")
    private String ownerName;

    @SerializedName("ownerSex")
    private String ownerSex;

    @SerializedName("ownerAddress")
    private String ownerAddress;

    public MedicalCard() {
    }

    public Long getMedicalCardNo() {
        return medicalCardNo;
    }

    public void setMedicalCardNo(Long medicalCardNo) {
        this.medicalCardNo = medicalCardNo;
    }

    public Long getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(Long ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public String getOwnerIdCard() {
        return ownerIdCard;
    }

    public void setOwnerIdCard(String ownerIdCard) {
        this.ownerIdCard = ownerIdCard;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerSex() {
        return ownerSex;
    }

    public void setOwnerSex(String ownerSex) {
        this.ownerSex = ownerSex;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

}
