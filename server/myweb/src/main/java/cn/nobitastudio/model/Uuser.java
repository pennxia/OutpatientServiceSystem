package cn.nobitastudio.model;

import java.io.Serializable;
import java.util.List;

public class Uuser  implements Serializable {


    private Long account;
    private String password;
    private String userName;

    private List<MedicalCard> medicalCards;
    private List<MyDoctor> myDoctors;

    public Uuser() {
    }

    public Uuser(Long account, String password, String userName) {
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

    public List<MedicalCard> getMedicalCards() {
        return medicalCards;
    }

    public void setMedicalCards(List<MedicalCard> medicalCards) {
        this.medicalCards = medicalCards;
    }

    public List<MyDoctor> getMyDoctors() {
        return myDoctors;
    }

    public void setMyDoctors(List<MyDoctor> myDoctors) {
        this.myDoctors = myDoctors;
    }
}
