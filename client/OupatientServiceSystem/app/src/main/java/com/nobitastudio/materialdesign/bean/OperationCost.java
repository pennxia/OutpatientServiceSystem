package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OperationCost implements Serializable {

    @SerializedName("operationCostId")
    String operationCostId;

    @SerializedName("operationItemId")
    String operationItemId;

    @SerializedName("purchaseNumber")
    Integer purchaseNumber;

    @SerializedName("allPrice")
    Double allPrice;

    @SerializedName("medicalCardNo")
    Long medicalCardNo;

    @SerializedName("createTime")
    String createTime;

    @SerializedName("account")
    Long account;

    @SerializedName("operationRoom")
    String operationRoom;

    @SerializedName("startTime")
    String startTime;

    @SerializedName("doctorNoteId")
    String doctorNoteId;

    public OperationCost() {
    }

    public String getOperationCostId() {
        return operationCostId;
    }

    public void setOperationCostId(String operationCostId) {
        this.operationCostId = operationCostId;
    }

    public String getOperationItemId() {
        return operationItemId;
    }

    public void setOperationItemId(String operationItemId) {
        this.operationItemId = operationItemId;
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

    public String getOperationRoom() {
        return operationRoom;
    }

    public void setOperationRoom(String operationRoom) {
        this.operationRoom = operationRoom;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDoctorNoteId() {
        return doctorNoteId;
    }

    public void setDoctorNoteId(String doctorNoteId) {
        this.doctorNoteId = doctorNoteId;
    }
}
