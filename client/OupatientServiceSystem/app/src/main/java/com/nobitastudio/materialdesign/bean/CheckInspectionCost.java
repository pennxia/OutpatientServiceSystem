package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckInspectionCost implements Serializable {

    @SerializedName("checkInspectionCostId")
    private String checkInspectionCostId;

    @SerializedName("checkInspectionItemId")
    private String checkInspectionItemId;

    @SerializedName("purchaseNumber")
    private Integer purchaseNumber;

    @SerializedName("allPrice")
    private Double allPrice;

    @SerializedName("checkInspectionTime")
    private String checkInspectionTime;

    @SerializedName("medicalCardNo")
    private Long medicalCardNo;

    @SerializedName("createTime")
    private String createTime;

    @SerializedName("account")
    private Long account;

    @SerializedName("checkTime")
    private String checkTime;

    @SerializedName("checkRoom")
    private String checkRoom;

    @SerializedName("doctorNoteId")
    private String doctorNoteId;

    public CheckInspectionCost() {
    }

    public String getCheckInspectionCostId() {
        return checkInspectionCostId;
    }

    public void setCheckInspectionCostId(String checkInspectionCostId) {
        this.checkInspectionCostId = checkInspectionCostId;
    }

    public String getCheckInspectionItemId() {
        return checkInspectionItemId;
    }

    public void setCheckInspectionItemId(String checkInspectionItemId) {
        this.checkInspectionItemId = checkInspectionItemId;
    }

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public Double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(Double allPrice) {
        this.allPrice = allPrice;
    }

    public String getCheckInspectionTime() {
        return checkInspectionTime;
    }

    public void setCheckInspectionTime(String checkInspectionTime) {
        this.checkInspectionTime = checkInspectionTime;
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

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckRoom() {
        return checkRoom;
    }

    public void setCheckRoom(String checkRoom) {
        this.checkRoom = checkRoom;
    }

    public String getDoctorNoteId() {
        return doctorNoteId;
    }

    public void setDoctorNoteId(String doctorNoteId) {
        this.doctorNoteId = doctorNoteId;
    }
}
