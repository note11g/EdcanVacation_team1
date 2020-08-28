package com.edcan.shareformproject;

import java.io.Serializable;

public class ChatModel implements Serializable {

    private UserModel from;
    private String to, time, text;

    public ChatModel() {
    }

    public ChatModel(UserModel from, String to, String time, String text) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.text = text;
    }

    public UserModel getFrom() {
        return from;
    }

    public void setFrom(UserModel from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
