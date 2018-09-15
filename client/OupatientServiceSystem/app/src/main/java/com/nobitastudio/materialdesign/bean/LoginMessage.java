package com.nobitastudio.materialdesign.bean;

import java.io.Serializable;

public class LoginMessage implements Serializable {

    private String account;

    private String password;

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

    public LoginMessage(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
