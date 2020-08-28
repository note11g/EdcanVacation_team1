package com.edcan.shareformproject.model;

public class UserModel {

    private String nick, phone, bank, acc, uid, profile;

    public UserModel(){}

    public UserModel(String nick, String phone, String bank, String acc, String uid, String profile) {
        this.nick = nick;
        this.phone = phone;
        this.bank = bank;
        this.acc = acc;
        this.uid = uid;
        this.profile = profile;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
