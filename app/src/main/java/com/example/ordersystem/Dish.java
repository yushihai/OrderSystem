package com.example.ordersystem;

import android.graphics.Bitmap;

public class Dish {

    private String name;
    private int price;
    private Bitmap image;
    private String description;
    private int sale;
    private int num;

    public Dish(String name, int price, Bitmap image, String description,int sale,int num) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.sale=sale;
        this.num=num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
