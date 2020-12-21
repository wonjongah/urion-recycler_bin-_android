package com.example.erion;


import java.sql.Timestamp;

public class Data {

    private String bottleKind;
    private Integer BottlePoint;
    private String date;
    private String resId;

    public String getBottleKind() {
        return bottleKind;
    }

    public void setBottleKind(String bottleKind) {
        this.bottleKind = bottleKind;
    }

    public Integer getBottlePoint() {
        return BottlePoint;
    }

    public void setBottlePoint(Integer BottlePoint) {
        this.BottlePoint = BottlePoint;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }
}