package cn.nobitastudio.model;

import java.io.Serializable;

public class Diagnosis  implements Serializable {

    private Integer diagnosisNo;

    private String registrationNo;

    public Diagnosis() {
    }

    public Diagnosis(Integer diagnosisNo, String registrationNo) {
        this.diagnosisNo = diagnosisNo;
        this.registrationNo = registrationNo;
    }

    public Integer getDiagnosisNo() {
        return diagnosisNo;
    }

    public void setDiagnosisNo(Integer diagnosisNo) {
        this.diagnosisNo = diagnosisNo;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }
}
