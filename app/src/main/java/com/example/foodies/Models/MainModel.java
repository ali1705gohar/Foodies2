package com.example.foodies.Models;

public class MainModel {

    int image;
    String prod_name,prod_price, prod_description;

    public MainModel(int image, String prod_name, String prod_price, String prod_description) {
        this.image = image;
        this.prod_name = prod_name;
        this.prod_price = prod_price;
        this.prod_description = prod_description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_price() {
        return prod_price;
    }

    public void setProd_price(String prod_price) {
        this.prod_price = prod_price;
    }

    public String getProd_description() {
        return prod_description;
    }

    public void setProd_description(String prod_description) {
        this.prod_description = prod_description;
    }
}
