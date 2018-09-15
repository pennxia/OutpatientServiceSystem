package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserVerificationCode implements Serializable{

    @SerializedName("account")
    private Long account;

    @SerializedName("verificationCode")
    private String verificationCode;

    @SerializedName("verificationCodeState")
    private Integer verificationCodeState; // 1 reference effictive 2 reference invalid

    @SerializedName("verificationCodeSendTime")
    private String verificationCodeSendTime;

    @SerializedName("sendResult")
    private String sendResult;

    public UserVerificationCode() {
    }

    public UserVerificationCode(Long account, String verificationCode, Integer verificationCodeState, String verificationCodeSendTime, String sendResult) {
        this.account = account;
        this.verificationCode = verificationCode;
        this.verificationCodeState = verificationCodeState;
        this.verificationCodeSendTime = verificationCodeSendTime;
        this.sendResult = sendResult;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Integer getVerificationCodeState() {
        return verificationCodeState;
    }

    public void setVerificationCodeState(Integer verificationCodeState) {
        this.verificationCodeState = verificationCodeState;
    }

    public String getVerificationCodeSendTime() {
        return verificationCodeSendTime;
    }

    public void setVerificationCodeSendTime(String verificationCodeSendTime) {
        this.verificationCodeSendTime = verificationCodeSendTime;
    }

    public String getSendResult() {
        return sendResult;
    }

    public void setSendResult(String sendResult) {
        this.sendResult = sendResult;
    }
}
