package cn.nobitastudio.model;

import java.io.Serializable;

public class DoctorWhetherHasNumber  implements Serializable {

    private Integer doctorId;

    private boolean doctorhasNumber;

    public DoctorWhetherHasNumber() {
    }

    public DoctorWhetherHasNumber(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public DoctorWhetherHasNumber(Integer doctorId, boolean doctorhasNumber) {
        this.doctorId = doctorId;
        this.doctorhasNumber = doctorhasNumber;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public boolean isDoctorhasNumber() {
        return doctorhasNumber;
    }

    public void setDoctorhasNumber(boolean doctorhasNumber) {
        this.doctorhasNumber = doctorhasNumber;
    }
}
