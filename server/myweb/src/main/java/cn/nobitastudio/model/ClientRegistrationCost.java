package cn.nobitastudio.model;

import java.io.Serializable;

public class ClientRegistrationCost implements Serializable {

    private Registration registration;

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
