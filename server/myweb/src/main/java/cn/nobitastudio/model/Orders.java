package cn.nobitastudio.model;

import java.io.Serializable;

/**
 * 订单类 与 其他类相互独立，其信息应该来源 HIS .
 * 但是为了模拟生成订单功能，在进行对 挂号单的操作时，生成挂号单时同时生成订单信息，并在进行支付操作时对订单信息进行修改
 * 而由于 药品单，检查检验单，手术单 等信息是由HIS 系统生成，所以其数据均为模拟数据，只有在进行支付相关操作时，对模拟的订单数据进行修改
 * 订单目前分为 4 类 ：挂号单(8001），药品单（8002），检查检验单（8003），手术单（8004）
 * 在 OrderBind 表中，用chargeItemType 来表示4种订单， 分别是 8001 8002 8003 8004
 */

public class Orders implements Serializable {

    private String orderName;

    private Integer orderState;

    private String orderCreateTime;

    private String payMethod;

    private String paySerialNumber;

    private Double allPrice;

    private String payOrCancelTime;

    public Orders() {
    }

    public Orders(String orderName, Integer orderState, String orderCreateTime, String payMethod, String paySerialNumber, Double allPrice, String payOrCancelTime) {
        this.orderName = orderName;
        this.orderState = orderState;
        this.orderCreateTime = orderCreateTime;
        this.payMethod = payMethod;
        this.paySerialNumber = paySerialNumber;
        this.allPrice = allPrice;
        this.payOrCancelTime = payOrCancelTime;

    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getPaySerialNumber() {
        return paySerialNumber;
    }

    public void setPaySerialNumber(String paySerialNumber) {
        this.paySerialNumber = paySerialNumber;
    }

    public Double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(Double allPrice) {
        this.allPrice = allPrice;
    }

    public String getPayOrCancelTime() {
        return payOrCancelTime;
    }

    public void setPayOrCancelTime(String payOrCancelTime) {
        this.payOrCancelTime = payOrCancelTime;
    }
}
