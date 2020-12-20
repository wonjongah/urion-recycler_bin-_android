package com.example.erion;


import java.sql.Timestamp;

public class Data {

    private String bottleKind;
    private Integer BottlePoint;
    private Timestamp date;
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

    public Timestamp getDate(){
        return date;
    }

    public void setDate(Timestamp date){
        this.date = date;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }
}