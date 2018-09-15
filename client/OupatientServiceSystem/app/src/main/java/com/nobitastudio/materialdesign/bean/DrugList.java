package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DrugList implements Serializable {

    @SerializedName("drugListId")
    private String drugListId;

    @SerializedName("drugId")
    private String drugId;

    @SerializedName("drugPurchaseQuantity")
    private Integer drugPurchaseQuantity;

    @SerializedName("allPrice")
    private Double allPrice;

    @SerializedName("doctorId")
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

}
