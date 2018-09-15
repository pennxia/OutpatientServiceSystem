package cn.nobitastudio.model;

import java.io.Serializable;

public class HealthNews  implements Serializable {

    private String healthNewsId;

    private String healthNewsIconName;

    private String healthNewsTitle;

    private Integer healthNewsClass;

    private String healthNewsPublishTime;

    private String healthNewsType;

    private String url;

    public HealthNews() {
    }

    public String getHealthNewsId() {
        return healthNewsId;
    }

    public void setHealthNewsId(String healthNewsId) {
        this.healthNewsId = healthNewsId;
    }

    public String getHealthNewsIconName() {
        return healthNewsIconName;
    }

    public void setHealthNewsIconName(String healthNewsIconName) {
        this.healthNewsIconName = healthNewsIconName;
    }

    public String getHealthNewsTitle() {
        return healthNewsTitle;
    }

    public void setHealthNewsTitle(String healthNewsTitle) {
        this.healthNewsTitle = healthNewsTitle;
    }

    public Integer getHealthNewsClass() {
        return healthNewsClass;
    }

    public void setHealthNewsClass(Integer healthNewsClass) {
        this.healthNewsClass = healthNewsClass;
    }

    public String getHealthNewsPublishTime() {
        return healthNewsPublishTime;
    }

    public void setHealthNewsPublishTime(String healthNewsPublishTime) {
        this.healthNewsPublishTime = healthNewsPublishTime;
    }

    public String getHealthNewsType() {
        return healthNewsType;
    }

    public void setHealthNewsType(String healthNewsType) {
        this.healthNewsType = healthNewsType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
