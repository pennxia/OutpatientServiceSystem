package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClientOperationData implements Serializable {

    @SerializedName("operationName")
    private String operationName;

    @SerializedName("patientName")
    private String patientName;

    @SerializedName("medicalCardNo")
    private Long medicalCardNo;

    @SerializedName("price")
    private Double price;

    @SerializedName("purchaseNumber")
    private Integer purchaseNumber;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("operationRoom")
    private String operationRoom;

    @SerializedName("orders")
    private Orders orders;

    @SerializedName("doctorNote")
    private DoctorNote doctorNote;

    public ClientOperationData() {
    }

    public ClientOperationData(String operationName, String patientName, Double price, Integer purchaseNumber, String startTime, String operationRoom, Long medicalCardNo, Orders orders, DoctorNote doctorNote) {
        this.operationName = operationName;
        this.patientName = patientName;
        this.price = price;
        this.purchaseNumber = purchaseNumber;
        this.startTime = startTime;
        this.operationRoom = operationRoom;
        this.orders = orders;
        this.doctorNote = doctorNote;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getOperationRoom() {
        return operationRoom;
    }

    public void setOperationRoom(String operationRoom) {
        this.operationRoom = operationRoom;
    }

    public Long getMedicalCardNo() {
        return medicalCardNo;
    }

    public void setMedicalCardNo(Long medicalCardNo) {
        this.medicalCardNo = medicalCardNo;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public DoctorNote getDoctorNote() {
        return doctorNote;
    }

    public void setDoctorNote(DoctorNote doctorNote) {
        this.doctorNote = doctorNote;
    }
}
