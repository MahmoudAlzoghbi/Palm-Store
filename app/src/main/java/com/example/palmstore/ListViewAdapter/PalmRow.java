package com.example.palmstore.ListViewAdapter;

public class PalmRow {
    private double price;
    private String farmer;
    private String seller;
    private String created_at;

    public PalmRow(double price, String farmer, String seller , String created_at) {
        this.price = price;
        this.farmer = farmer;
        this.seller = seller;
        this.created_at = created_at;
    }

    public double getPrice() {
        return price;
    }

    public String getFarmer() {
        return farmer;
    }

    public String getSeller() {
        return seller;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setFarmer(String farmer) {
        this.farmer = farmer;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
