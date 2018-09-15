package com.nobitastudio.materialdesign.bean;

public class PaymentSimpleData {

    private Integer paymentItemType;  // 1 references drugCost, 2 references operationCost  3 references checkInspectionCost.

    private String itemCostId;

    private Double cost;

    private Integer payState;

    private String payOrCancelTime;


    public PaymentSimpleData() {
    }

    public PaymentSimpleData(Integer paymentItemType, String itemCostId, Double cost, Integer payState, String payOrCancelTime) {
        this.paymentItemType = paymentItemType;
        this.itemCostId = itemCostId;
        this.cost = cost;
        this.payState = payState;
        this.payOrCancelTime = payOrCancelTime;
    }

    public Integer getPaymentItemType() {
        return paymentItemType;
    }

    public void setPaymentItemType(Integer paymentItemType) {
        this.paymentItemType = paymentItemType;
    }

    public String getItemCostId() {
        return itemCostId;
    }

    public void setItemCostId(String itemCostId) {
        this.itemCostId = itemCostId;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public String getPayOrCancelTime() {
        return payOrCancelTime;
    }

    public void setPayOrCancelTime(String payOrCancelTime) {
        this.payOrCancelTime = payOrCancelTime;
    }
}
