package cn.nobitastudio.model;

import java.io.Serializable;

public class ClientDrugCost implements Serializable {

    private DrugCost drugCost;

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
