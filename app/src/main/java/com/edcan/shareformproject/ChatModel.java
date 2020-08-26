package com.edcan.shareformproject;

import java.io.Serializable;

public class ChatModel implements Serializable {
    private String time, text, email;

    public ChatModel() {
    }

    public ChatModel(String time, String text, String email) {
        this.time = time;
        this.text = text;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
