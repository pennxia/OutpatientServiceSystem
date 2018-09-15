package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{

    @SerializedName("account")
    private String userAccount;

    @SerializedName("password")
    private String userPassword;

    @SerializedName("userName")
    private String userName;

    @SerializedName("medicalCards")
    private List<MedicalCard> medicalCards;

    @SerializedName("myDoctors")
    private List<MyDoctor> myDoctors;

    public List<MedicalCard> getMedicalCards() {
        return medicalCards;
    }

    public void setMedicalCards(List<MedicalCard> medicalCards) {
        this.medicalCards = medicalCards;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<MyDoctor> getMyDoctors() {
        return myDoctors;
    }

    public void setMyDoctors(List<MyDoctor> myDoctors) {
        this.myDoctors = myDoctors;
    }

    public User() {
    }

}
