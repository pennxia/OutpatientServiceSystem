package cn.nobitastudio.model;

import java.io.Serializable;
import java.util.Date;

public class RegistrationForm  implements Serializable {

    private String outpatientName;
    private String doctorName;
    private String medicalCardOwnerName;
    private Date yearMonthDate;
    private String timeSlot;
    private String diagnosisNo;
    private String cost;
    private String address;
    private String appointSource;

    public RegistrationForm() {
    }

    public String getOutpatientName() {
        return outpatientName;
    }

    public void setOutpatientName(String outpatientName) {
        this.outpatientName = outpatientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMedicalCardOwnerName() {
        return medicalCardOwnerName;
    }

    public void setMedicalCardOwnerName(String medicalCardOwnerName) {
        this.medicalCardOwnerName = medicalCardOwnerName;
    }

    public Date getYearMonthDate() {
        return yearMonthDate;
    }

    public void setYearMonthDate(Date yearMonthDate) {
        this.yearMonthDate = yearMonthDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getDiagnosisNo() {
        return diagnosisNo;
    }

    public void setDiagnosisNo(String diagnosisNo) {
        this.diagnosisNo = diagnosisNo;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppointSource() {
        return appointSource;
    }

    public void setAppointSource(String appointSource) {
        this.appointSource = appointSource;
    }


}
