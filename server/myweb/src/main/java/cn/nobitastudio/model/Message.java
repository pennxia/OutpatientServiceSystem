package cn.nobitastudio.model;

import java.io.Serializable;

public class Message  implements Serializable {

    private boolean success;

    private String describe;

    public Message(boolean success, String describe) {
        this.success = success;
        this.describe = describe;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
