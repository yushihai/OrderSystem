package com.example.ordersystem;

public class Order {

    private String name;
    private int num;
    private int price;

    public Order(String name, int num, int price) {
        this.name = name;
        this.num = num;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
