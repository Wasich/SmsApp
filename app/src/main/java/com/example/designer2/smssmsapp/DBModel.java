package com.example.designer2.smssmsapp;

public class DBModel {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String sms;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return sms;
    }

    public void setMessage(String message) {
        this.sms = message;
    }
}
