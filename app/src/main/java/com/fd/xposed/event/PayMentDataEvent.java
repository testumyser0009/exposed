package com.fd.xposed.event;

public class PayMentDataEvent {

    private String data;

    public PayMentDataEvent(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
