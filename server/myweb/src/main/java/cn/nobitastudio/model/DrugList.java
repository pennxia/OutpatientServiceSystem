package cn.nobitastudio.model;

import java.io.Serializable;

public class DrugList implements Serializable {

    private String drugListId;

    private String drugId;

    private Integer drugPurchaseQuantity;

    private Double allPrice;

    private Integer doctorId;

    public DrugList() {
    }

    public String getDrugListId() {
        return drugListId;
    }

    public void setDrugListId(String drugListId) {
        this.drugListId = drugListId;
    }

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public Integer getDrugPurchaseQuantity() {
        return drugPurchaseQuantity;
    }

    public void setDrugPurchaseQuantity(Integer drugPurchaseQuantity) {
        this.drugPurchaseQuantity = drugPurchaseQuantity;
    }

    public Double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(Double allPrice) {
        this.allPrice = allPrice;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }
}
