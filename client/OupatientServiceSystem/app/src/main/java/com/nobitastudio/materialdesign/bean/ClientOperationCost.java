package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClientOperationCost implements Serializable {

    @SerializedName("operationCost")
    private OperationCost operationCost;

    @SerializedName("orders")
    private Orders orders;

    public ClientOperationCost() {
    }

    public ClientOperationCost(OperationCost operationCost, Orders orders) {
        this.operationCost = operationCost;
        this.orders = orders;
    }

    public OperationCost getOperationCost() {
        return operationCost;
    }

    public void setOperationCost(OperationCost operationCost) {
        this.operationCost = operationCost;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
