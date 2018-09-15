package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Visit implements Serializable {

    @SerializedName("visitNo")
    private Long visitNo;

    @SerializedName("doctorId")
    private Integer doctorId;

    @SerializedName("cost")
    private Float cost;

    @SerializedName("years")
    private YearMonthDate years;

    @SerializedName("week")
    private String week;

    @SerializedName("timeSlot")
    private String timeSlot;

    @SerializedName("amount")
    private Integer amount;

    @SerializedName("leftAmount")
    private Integer leftAmount;

    public Visit() {
    }

    public Visit(Long visitNo, Integer doctorId, Float cost, YearMonthDate years, String week, String timeSlot, Integer amount, Integer leftAmount) {
        this.visitNo = visitNo;
        this.doctorId = doctorId;
        this.cost = cost;
        this.years = years;
        this.week = week;
        this.timeSlot = timeSlot;
        this.amount = amount;
        this.leftAmount = leftAmount;
    }

    public Long getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(Long visitNo) {
        this.visitNo = visitNo;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public YearMonthDate getYears() {
        return years;
    }

    public void setYears(YearMonthDate years) {
        this.years = years;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(Integer leftAmount) {
        this.leftAmount = leftAmount;
    }


}
