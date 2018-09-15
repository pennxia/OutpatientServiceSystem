package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClientDrugCost implements Serializable {

    @SerializedName("drugCost")
    private DrugCost drugCost;

    @SerializedName("orders")
    private Orders orders;

    public ClientDrugCost() {
    }

    public ClientDrugCost(DrugCost drugCost, Orders orders) {
        this.drugCost = drugCost;
        this.orders = orders;
    }

    public DrugCost getDrugCost() {
        return drugCost;
    }

    public void setDrugCost(DrugCost drugCost) {
        this.drugCost = drugCost;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

}
