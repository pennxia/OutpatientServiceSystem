package cn.nobitastudio.model;

import java.io.Serializable;

public class MyDoctor  implements Serializable {

    private Long account;

    private Integer doctorId;

    public MyDoctor() {
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

}
