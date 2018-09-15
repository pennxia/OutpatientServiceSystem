package cn.nobitastudio.model;

import java.io.Serializable;

public class EnrollResult  implements Serializable {

    private boolean success;

    private String account;

    private String password;

    public EnrollResult() {
    }

    public EnrollResult(boolean success, String account, String password) {
        this.success = success;
        this.account = account;
        this.password = password;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
