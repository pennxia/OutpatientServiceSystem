package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class YearMonthDate implements Serializable {

    @SerializedName("date")
    private Integer date;

    @SerializedName("day")
    private Integer day;

    @SerializedName("hours")
    private Integer hours;

    @SerializedName("minutes")
    private Integer minutes;

    @SerializedName("month")
    private Integer month;

    @SerializedName("nanos")
    private Integer nanos;

    @SerializedName("seconds")
    private Integer seconds;

    @SerializedName("time")
    private Long time;

    @SerializedName("timezoneOffset")
    private Integer timezoneOffset;

    @SerializedName("year")
    private Integer year;

    public YearMonthDate() {
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getNanos() {
        return nanos;
    }

    public void setNanos(Integer nanos) {
        this.nanos = nanos;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(Integer timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
