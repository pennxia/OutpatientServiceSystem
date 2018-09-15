package cn.nobitastudio.model;

import java.io.Serializable;

public class ClientOperationCost implements Serializable {

    private OperationCost operationCost;

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
