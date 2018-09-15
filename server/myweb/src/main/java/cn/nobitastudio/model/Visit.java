package cn.nobitastudio.model;


import java.io.Serializable;
import java.util.Date;

public class Visit  implements Serializable {

    private Long visitNo;
    private Integer doctorId;
    private Float cost;
    private Date years;
    private String week;
    private String timeSlot;
    private Integer amount;
    private Integer leftAmount;

    public Visit() {
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

    public Date getYears() {
        return years;
    }

    public void setYears(Date years) {
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
