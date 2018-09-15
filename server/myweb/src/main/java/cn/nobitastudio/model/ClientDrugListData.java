package cn.nobitastudio.model;

import java.io.Serializable;
import java.util.List;

public class ClientDrugListData implements Serializable {

    private List<ClientDrugList> clientDrugLists;

    private String patientName;

    private Long medicalCardNo;

    private String diagnosisDoctorName;

    private Orders orders;

    private DoctorNote doctorNote;

    public ClientDrugListData() {
    }

    public ClientDrugListData(List<ClientDrugList> clientDrugLists, String patientName, Long medicalCardNo, String diagnosisDoctorName, Orders orders, DoctorNote doctorNote) {
        this.clientDrugLists = clientDrugLists;
        this.patientName = patientName;
        this.medicalCardNo = medicalCardNo;
        this.diagnosisDoctorName = diagnosisDoctorName;
        this.orders = orders;
        this.doctorNote = doctorNote;
    }

    public List<ClientDrugList> getClientDrugLists() {
        return clientDrugLists;
    }

    public void setClientDrugLists(List<ClientDrugList> clientDrugLists) {
        this.clientDrugLists = clientDrugLists;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Long getMedicalCardNo() {
        return medicalCardNo;
    }

    public void setMedicalCardNo(Long medicalCardNo) {
        this.medicalCardNo = medicalCardNo;
    }

    public String getDiagnosisDoctorName() {
        return diagnosisDoctorName;
    }

    public void setDiagnosisDoctorName(String diagnosisDoctorName) {
        this.diagnosisDoctorName = diagnosisDoctorName;
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
