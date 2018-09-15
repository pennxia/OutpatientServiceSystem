package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OperationItem implements Serializable {

    @SerializedName("operationItemId")
    private String operationItemId;

    @SerializedName("operationItemName")
    private String operationItemName;

    @SerializedName("price")
    private Double price;

    public OperationItem() {
    }

    public String getOperationItemId() {
        return operationItemId;
    }

    public void setOperationItemId(String operationItemId) {
        this.operationItemId = operationItemId;
    }

    public String getOperationItemName() {
        return operationItemName;
    }

    public void setOperationItemName(String operationItemName) {
        this.operationItemName = operationItemName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
