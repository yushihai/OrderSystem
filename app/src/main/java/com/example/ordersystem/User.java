package com.example.ordersystem;

public class User {

    private String user;
    private String password;
    private String qq;
    private String email;
    private int headportrait;
    private int type;

    public User(String user, String password, String qq, String email, int headportrait, int type) {
        this.user = user;
        this.password = password;
        this.qq = qq;
        this.email = email;
        this.headportrait = headportrait;
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHeadportrait() {
        return headportrait;
    }

    public void setHeadportrait(int headportrait) {
        this.headportrait = headportrait;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
