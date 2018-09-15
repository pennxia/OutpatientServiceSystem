package cn.nobitastudio.model;

import java.io.Serializable;

public class OperationCost implements Serializable {

    private String operationCostId;

    private String operationItemId;

    private Integer purchaseNumber;

    private Double allPrice;

    private  Long medicalCardNo;

    private  String createTime;

    private  Long account;

    private String operationRoom;

    private String startTime;

    private String doctorNoteId;

    public OperationCost() {
    }

    public OperationCost(String operationCostId, String operationItemId, Integer purchaseNumber, Double allPrice, Long medicalCardNo, String createTime, Long account, String operationRoom, String startTime, String doctorNoteId) {
        this.operationCostId = operationCostId;
        this.operationItemId = operationItemId;
        this.purchaseNumber = purchaseNumber;
        this.allPrice = allPrice;
        this.medicalCardNo = medicalCardNo;
        this.createTime = createTime;
        this.account = account;
        this.operationRoom = operationRoom;
        this.startTime = startTime;
        this.doctorNoteId = doctorNoteId;
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
