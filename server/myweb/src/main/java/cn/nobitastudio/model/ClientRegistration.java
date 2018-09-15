package cn.nobitastudio.model;

import java.io.Serializable;

public class ClientRegistration  implements Serializable {


    private String registrationNo;
    private Float cost;
    private Integer orderState;
    private String payOrCancelTime;

    public ClientRegistration() {
    }

    public ClientRegistration(String registrationNo, Float cost, Integer orderState, String payOrCancelTime) {
        this.registrationNo = registrationNo;
        this.cost = cost;
        this.orderState = orderState;
        this.payOrCancelTime = payOrCancelTime;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getPayOrCancelTime() {
        return payOrCancelTime;
    }

    public void setPayOrCancelTime(String payOrCancelTime) {
        this.payOrCancelTime = payOrCancelTime;
    }
}
