package cn.nobitastudio.model;

/**
 * 订单目前分为 4 类 ：挂号单(8001），药品单（8002），检查检验单（8003），手术单（8004）
 * 用chargeItemType 来表示4种订单， 分别是 8001 8002 8003 8004
 */
public class OrderBind {

    private String paySerialNumber;

    private Integer chargeItemType;

    private String chargeItemId;

    public OrderBind() {
    }

    public OrderBind(String paySerialNumber, Integer chargeItemType, String chargeItemId) {
        this.paySerialNumber = paySerialNumber;
        this.chargeItemType = chargeItemType;
        this.chargeItemId = chargeItemId;
    }

    public String getPaySerialNumber() {
        return paySerialNumber;
    }

    public void setPaySerialNumber(String paySerialNumber) {
        this.paySerialNumber = paySerialNumber;
    }

    public Integer getChargeItemType() {
        return chargeItemType;
    }

    public void setChargeItemType(Integer chargeItemType) {
        this.chargeItemType = chargeItemType;
    }

    public String getChargeItemId() {
        return chargeItemId;
    }

    public void setChargeItemId(String chargeItemId) {
        this.chargeItemId = chargeItemId;
    }
}
