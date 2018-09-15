package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Drug implements Serializable {

    @SerializedName("drugId")
    private String drugId;

    @SerializedName("drugName")
    private String drugName;

    @SerializedName("drugPrice")
    private Double drugPrice;

    @SerializedName("drugPurchasePrice")
    private Double drugPurchasePrice;

    @SerializedName("drugManufacturer")
    private String drugManufacturer;

    @SerializedName("drugManufactureDate")
    private String drugManufactureDate;

    @SerializedName("drugEffectiveDate")
    private Integer drugEffectiveDate;

    public Drug() {
    }

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Double getDrugPrice() {
        return drugPrice;
    }

    public void setDrugPrice(Double drugPrice) {
        this.drugPrice = drugPrice;
    }

    public Double getDrugPurchasePrice() {
        return drugPurchasePrice;
    }

    public void setDrugPurchasePrice(Double drugPurchasePrice) {
        this.drugPurchasePrice = drugPurchasePrice;
    }

    public String getDrugManufacturer() {
        return drugManufacturer;
    }

    public void setDrugManufacturer(String drugManufacturer) {
        this.drugManufacturer = drugManufacturer;
    }

    public String getDrugManufactureDate() {
        return drugManufactureDate;
    }

    public void setDrugManufactureDate(String drugManufactureDate) {
        this.drugManufactureDate = drugManufactureDate;
    }

    public Integer getDrugEffectiveDate() {
        return drugEffectiveDate;
    }

    public void setDrugEffectiveDate(Integer drugEffectiveDate) {
        this.drugEffectiveDate = drugEffectiveDate;
    }
}
