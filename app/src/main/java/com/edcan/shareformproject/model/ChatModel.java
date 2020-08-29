package com.edcan.shareformproject.model;

import java.io.Serializable;
import java.util.HashMap;

public class ChatModel implements Serializable {

    private String from, time, text;

    public ChatModel() {}

    public static HashMap<String, String> toMap(ChatModel model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("from", model.from);
        map.put("time", model.time);
        map.put("text", model.text);
        return map;
    }

    public static ChatModel fromMap(HashMap<String, String> map) {
        ChatModel model = new ChatModel();
        model.setFrom(map.get("from"));
        model.setTime(map.get("time"));
        model.setText(map.get("text"));
        return model;
    }

    public ChatModel(String from, String time, String text) {
        this.from = from;
        this.time = time;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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
