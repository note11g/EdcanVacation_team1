package com.edcan.shareformproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NowShareModel implements Serializable {

    private List<String> member;
    private List<HashMap<String, String>> message;

    private String room, image, name, text;

    public NowShareModel() {
    }

    public NowShareModel(List<String> member, List<HashMap<String, String>> message, String room, String image, String name, String text) {
        this.member = member;
        this.message = message;
        this.room = room;
        this.image = image;
        this.name = name;
        this.text = text;
    }

    public List<String> getMember() {
        return member;
    }

    public void setMember(List<String> member) {
        this.member = member;
    }

    public List<ChatModel> getMessage() {
        List<ChatModel> list = new ArrayList<>();
        for (HashMap<String, String> m : message) {
            list.add(ChatModel.fromMap(m));
        }
        return list;
    }

    public void setMessage(List<HashMap<String, String>> message) {
        this.message = message;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
