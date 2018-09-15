package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckInspectionItem implements Serializable {

    @SerializedName("CheckInspectionItemId")
    private String CheckInspectionItemId;

    @SerializedName("CheckInspectionItemName")
    private String CheckInspectionItemName;

    @SerializedName("price")
    private Double price;

    public CheckInspectionItem() {
    }

    public String getCheckInspectionItemId() {
        return CheckInspectionItemId;
    }

    public void setCheckInspectionItemId(String checkInspectionItemId) {
        CheckInspectionItemId = checkInspectionItemId;
    }

    public String getCheckInspectionItemName() {
        return CheckInspectionItemName;
    }

    public void setCheckInspectionItemName(String checkInspectionItemName) {
        CheckInspectionItemName = checkInspectionItemName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
