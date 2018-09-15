package cn.nobitastudio.model;

import java.io.Serializable;

public class EnrollMessage  implements Serializable {

    private Long account;
    private String password;
    private String userName;

    public EnrollMessage() {
    }

    public EnrollMessage(Long account, String password, String userName) {
        this.account = account;
        this.password = password;
        this.userName = userName;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
