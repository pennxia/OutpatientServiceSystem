package cn.nobitastudio.model;


import java.io.Serializable;

public class Registration  implements Serializable {

    private String registrationNo;
    private Long account;
    private Long visitNo;
    private Long medicalCardNo;
    private Integer diagnosisNo;
    private String createTime;

    public Registration() {
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public Long getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(Long visitNo) {
        this.visitNo = visitNo;
    }

    public Long getMedicalCardNo() {
        return medicalCardNo;
    }

    public void setMedicalCardNo(Long medicalCardNo) {
        this.medicalCardNo = medicalCardNo;
    }

    public Integer getDiagnosisNo() {
        return diagnosisNo;
    }

    public void setDiagnosisNo(Integer diagnosisNo) {
        this.diagnosisNo = diagnosisNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
