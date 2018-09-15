package cn.nobitastudio.model;

import java.io.Serializable;
import java.util.List;

public class LoginResult  implements Serializable {

    private Uuser uuser;

    private List<HospitalActivity> hospitalActivityList;

    private List<HealthNews> healthNewsList;

    private List<SimpleHealthNews> simpleHealthNewsList;

    public LoginResult() {
    }

    public Uuser getUuser() {
        return uuser;
    }

    public void setUuser(Uuser uuser) {
        this.uuser = uuser;
    }

    public List<HospitalActivity> getHospitalActivityList() {
        return hospitalActivityList;
    }

    public void setHospitalActivityList(List<HospitalActivity> hospitalActivityList) {
        this.hospitalActivityList = hospitalActivityList;
    }

    public List<HealthNews> getHealthNewsList() {
        return healthNewsList;
    }

    public void setHealthNewsList(List<HealthNews> healthNewsList) {
        this.healthNewsList = healthNewsList;
    }

    public List<SimpleHealthNews> getSimpleHealthNewsList() {
        return simpleHealthNewsList;
    }

    public void setSimpleHealthNewsList(List<SimpleHealthNews> simpleHealthNewsList) {
        this.simpleHealthNewsList = simpleHealthNewsList;
    }
}
