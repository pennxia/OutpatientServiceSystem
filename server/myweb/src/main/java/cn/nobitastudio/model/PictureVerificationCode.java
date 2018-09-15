package cn.nobitastudio.model;

import java.io.Serializable;

public class PictureVerificationCode  implements Serializable {

    private Integer pictureVerificationCodeId;

    private Integer verificationCode;

    private String pictureVerificationCodeName;

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
