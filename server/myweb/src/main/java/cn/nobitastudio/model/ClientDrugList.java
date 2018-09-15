package cn.nobitastudio.model;

import java.io.Serializable;

public class ClientDrugList implements Serializable {

    private String drugName;

    private Integer purchaseNumber;

    private Double price;

    private Double allPrice;

    public ClientDrugList() {
    }

    public ClientDrugList(String drugName, Integer purchaseNumber, Double price, Double allPrice) {
        this.drugName = drugName;
        this.purchaseNumber = purchaseNumber;
        this.price = price;
        this.allPrice = allPrice;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(Double allPrice) {
        this.allPrice = allPrice;
    }
}
