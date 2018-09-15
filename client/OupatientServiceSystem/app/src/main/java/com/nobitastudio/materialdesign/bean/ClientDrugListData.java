package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientDrugListData implements Serializable {

    @SerializedName("clientDrugLists")
    private List<ClientDrugList> clientDrugLists;

    @SerializedName("patientName")
    private String patientName;

    @SerializedName("medicalCardNo")
    private Long medicalCardNo;

    @SerializedName("diagnosisDoctorName")
    private String diagnosisDoctorName;

    @SerializedName("orders")
    private Orders orders;

    @SerializedName("doctorNote")
    private DoctorNote doctorNote;

    public ClientDrugListData() {
        clientDrugLists = new ArrayList<>();
    }

    public ClientDrugListData(List<ClientDrugList> clientDrugLists, String patientName, String diagnosisDoctorName, Long medicalCardNo, Orders orders, DoctorNote doctorNote) {
        this.clientDrugLists = clientDrugLists;
        this.patientName = patientName;
        this.diagnosisDoctorName = diagnosisDoctorName;
        this.medicalCardNo = medicalCardNo;
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

    public String getDiagnosisDoctorName() {
        return diagnosisDoctorName;
    }

    public void setDiagnosisDoctorName(String diagnosisDoctorName) {
        this.diagnosisDoctorName = diagnosisDoctorName;
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
