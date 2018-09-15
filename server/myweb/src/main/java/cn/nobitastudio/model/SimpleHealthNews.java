package cn.nobitastudio.model;

import java.io.Serializable;

public class SimpleHealthNews implements Serializable {

    private String simpleHealthNewsId;

    private String simpleHealthNewsIconName;

    private String url;

    public SimpleHealthNews() {
    }

    public String getSimpleHealthNewsId() {
        return simpleHealthNewsId;
    }

    public void setSimpleHealthNewsId(String simpleHealthNewsId) {
        this.simpleHealthNewsId = simpleHealthNewsId;
    }

    public String getSimpleHealthNewsIconName() {
        return simpleHealthNewsIconName;
    }

    public void setSimpleHealthNewsIconName(String simpleHealthNewsIconName) {
        this.simpleHealthNewsIconName = simpleHealthNewsIconName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
