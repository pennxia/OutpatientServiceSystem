package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClientCheckInspectionCost implements Serializable {

    @SerializedName("checkInspectionCost")
    private CheckInspectionCost checkInspectionCost;

    @SerializedName("orders")
    private Orders orders;

    public ClientCheckInspectionCost() {
    }

    public ClientCheckInspectionCost(CheckInspectionCost checkInspectionCost, Orders orders) {
        this.checkInspectionCost = checkInspectionCost;
        this.orders = orders;
    }

    public CheckInspectionCost getCheckInspectionCost() {
        return checkInspectionCost;
    }

    public void setCheckInspectionCost(CheckInspectionCost checkInspectionCost) {
        this.checkInspectionCost = checkInspectionCost;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
