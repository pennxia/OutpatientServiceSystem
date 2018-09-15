package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PictureVerificationCode implements Serializable {

    @SerializedName("pictureVerificationCodeId")
    private Integer pictureVerificationCodeId;

    @SerializedName("verificationCode")
    private Integer verificationCode;

    @SerializedName("pictureVerificationCodeName")
    private String pictureVerificationCodeName;

    @SerializedName("url")
    private String url;

    public PictureVerificationCode() {
    }

    public Integer getPictureVerificationCodeId() {
        return pictureVerificationCodeId;
    }

    public void setPictureVerificationCodeId(Integer pictureVerificationCodeId) {
        this.pictureVerificationCodeId = pictureVerificationCodeId;
    }

    public Integer getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(Integer verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getPictureVerificationCodeName() {
        return pictureVerificationCodeName;
    }

    public void setPictureVerificationCodeName(String pictureVerificationCodeName) {
        this.pictureVerificationCodeName = pictureVerificationCodeName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
