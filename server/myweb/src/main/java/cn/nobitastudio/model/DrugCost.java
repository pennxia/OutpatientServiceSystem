package cn.nobitastudio.model;

import java.io.Serializable;

public class DrugCost implements Serializable {

    private String drugCostId;

    private String drugListId;

    private Double allPrice;

    private Long medicalCardNo;

    private String createTime;

    private Long account;

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
