package com.example.foodies.Models;

public class OrdersModel {
    int order_image;
    String order_name, order_price, order_number;

    public OrdersModel(int order_image, String order_name, String order_price, String order_number) {
        this.order_image = order_image;
        this.order_name = order_name;
        this.order_price = order_price;
        this.order_number = order_number;
    }

    public int getOrder_image() {
        return order_image;
    }

    public void setOrder_image(int order_image) {
        this.order_image = order_image;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }
}
