package cn.nobitastudio.model;

import java.io.Serializable;

public class CheckInspectionCost implements Serializable {

    private String checkInspectionCostId;

    private String checkInspectionItemId;

    private Integer purchaseNumber;

    private Double allPrice;

    private String checkInspectionTime;

    private Long medicalCardNo;

    private String createTime;

    private Long account;

    private String checkTime;

    private String checkRoom;

    private String doctorNoteId;

    public CheckInspectionCost() {
    }

    public CheckInspectionCost(String checkInspectionCostId, String checkInspectionItemId, Integer purchaseNumber, Double allPrice, String checkInspectionTime, Long medicalCardNo, String createTime, Long account, String checkTime, String checkRoom, String doctorNoteId) {
        this.checkInspectionCostId = checkInspectionCostId;
        this.checkInspectionItemId = checkInspectionItemId;
        this.purchaseNumber = purchaseNumber;
        this.allPrice = allPrice;
        this.checkInspectionTime = checkInspectionTime;
        this.medicalCardNo = medicalCardNo;
        this.createTime = createTime;
        this.account = account;
        this.checkTime = checkTime;
        this.checkRoom = checkRoom;
        this.doctorNoteId = doctorNoteId;
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
