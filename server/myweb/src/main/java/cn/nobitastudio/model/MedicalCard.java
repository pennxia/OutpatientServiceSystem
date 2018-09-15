package cn.nobitastudio.model;

import java.io.Serializable;

public class MedicalCard  implements Serializable {

    private Long medicalCardNo;
    private String ownerName;
    private String ownerIdCard;
    private String ownerSex;
    private String ownerAddress;
    private Long ownerAccount;

    public MedicalCard() {
    }

    public MedicalCard(Long medicalCardNo, String ownerName, String ownerIdCard, String ownerSex, String ownerAddress, Long ownerAccount) {
        this.medicalCardNo = medicalCardNo;
        this.ownerName = ownerName;
        this.ownerIdCard = ownerIdCard;
        this.ownerSex = ownerSex;
        this.ownerAddress = ownerAddress;
        this.ownerAccount = ownerAccount;
    }

    public Long getMedicalCardNo() {
        return medicalCardNo;
    }

    public void setMedicalCardNo(Long medicalCardNo) {
        this.medicalCardNo = medicalCardNo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerIdCard() {
        return ownerIdCard;
    }

    public void setOwnerIdCard(String ownerIdCard) {
        this.ownerIdCard = ownerIdCard;
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

    public Long getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(Long ownerAccount) {
        this.ownerAccount = ownerAccount;
    }
}
