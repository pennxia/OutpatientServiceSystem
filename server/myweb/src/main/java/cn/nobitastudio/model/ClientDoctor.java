package cn.nobitastudio.model;

import java.io.Serializable;

public class ClientDoctor  implements Serializable {

    private Doctor doctor;

    private Department department;

    private String hospitalName;

    public ClientDoctor() {
    }

    public ClientDoctor(Doctor doctor, Department department, String hospitalName) {
        this.doctor = doctor;
        this.department = department;
        this.hospitalName = hospitalName;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
