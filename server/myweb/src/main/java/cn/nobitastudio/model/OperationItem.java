package cn.nobitastudio.model;

import java.io.Serializable;

public class OperationItem implements Serializable {

    private String operationItemId;

    private String operationItemName;

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
