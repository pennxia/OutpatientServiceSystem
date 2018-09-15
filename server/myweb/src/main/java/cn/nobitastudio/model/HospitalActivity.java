package cn.nobitastudio.model;

import java.io.Serializable;

public class HospitalActivity  implements Serializable {

    private String hospitalActivityId;

    private String hospitalActivityIconName;

    private String url;

    public HospitalActivity() {
    }

    public String getHospitalActivityId() {
        return hospitalActivityId;
    }

    public void setHospitalActivityId(String hospitalActivityId) {
        this.hospitalActivityId = hospitalActivityId;
    }

    public String getHospitalActivityIconName() {
        return hospitalActivityIconName;
    }

    public void setHospitalActivityIconName(String hospitalActivityIconName) {
        this.hospitalActivityIconName = hospitalActivityIconName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
