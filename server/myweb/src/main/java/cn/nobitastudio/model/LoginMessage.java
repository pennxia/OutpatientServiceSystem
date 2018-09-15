package cn.nobitastudio.model;

import java.io.Serializable;

public class LoginMessage  implements Serializable {

    private String userAccount;
    private String UserPassword;

    public LoginMessage() {
    }

    public LoginMessage(String userAccount, String userPassword) {
        this.userAccount = userAccount;
        UserPassword = userPassword;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }


}
