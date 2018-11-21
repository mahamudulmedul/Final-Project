package com.example.sazzad.farmersapp.Model;

import java.util.Date;

public class UpdatePrice {
    private String productName;
    private String quantity;
    private String price;
    private String place;
    private String date;

    public UpdatePrice() {
    }

    public UpdatePrice(String productName, String quantity, String price, String place, String date) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.place = place;
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
