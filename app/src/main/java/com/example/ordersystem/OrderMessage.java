package com.example.ordersystem;

public class OrderMessage {

    private String order_id;
    private String order_time;
    private String order_state;
    private int order_total;
    private String order_name;

    public OrderMessage(String order_id, String order_time, String order_state, int order_total, String order_name) {
        this.order_id = order_id;
        this.order_time = order_time;
        this.order_state = order_state;
        this.order_total = order_total;
        this.order_name = order_name;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public int getOrder_total() {
        return order_total;
    }

    public void setOrder_total(int order_total) {
        this.order_total = order_total;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }
}
