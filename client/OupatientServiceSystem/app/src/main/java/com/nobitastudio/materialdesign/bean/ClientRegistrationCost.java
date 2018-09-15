package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClientRegistrationCost implements Serializable {

    @SerializedName("registration")
    private Registration registration;

    @SerializedName("orders")
    private Orders orders;

    public ClientRegistrationCost() {
    }

    public ClientRegistrationCost(Registration registration, Orders orders) {
        this.registration = registration;
        this.orders = orders;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
