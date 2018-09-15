package cn.nobitastudio.model;

import java.io.Serializable;

public class ClientCheckInspectionData implements Serializable {


    private String checkInspectionItemName;

    private String patientName;

    private Long medicalCardNo;

    private Double price;

    private Integer purchaseNumber;

    private String checkTime;

    private String checkInspectionRoom;

    private Orders orders;

    private DoctorNote doctorNote;

    public ClientCheckInspectionData() {
    }

    public ClientCheckInspectionData(String checkInspectionItemName, String patientName, Double price, Integer purchaseNumber, String checkTime, String checkInspectionRoom, Long medicalCardNo,Orders orders, DoctorNote doctorNote) {
        this.checkInspectionItemName = checkInspectionItemName;
        this.patientName = patientName;
        this.price = price;
        this.purchaseNumber = purchaseNumber;
        this.checkTime = checkTime;
        this.checkInspectionRoom = checkInspectionRoom;
        this.medicalCardNo = medicalCardNo;
        this.orders = orders;
        this.doctorNote = doctorNote;
    }

    public String getCheckInspectionItemName() {
        return checkInspectionItemName;
    }

    public void setCheckInspectionItemName(String checkInspectionItemName) {
        this.checkInspectionItemName = checkInspectionItemName;
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

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckInspectionRoom() {
        return checkInspectionRoom;
    }

    public void setCheckInspectionRoom(String checkInspectionRoom) {
        this.checkInspectionRoom = checkInspectionRoom;
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
