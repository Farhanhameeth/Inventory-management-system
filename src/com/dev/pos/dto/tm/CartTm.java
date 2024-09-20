package com.dev.pos.dto.tm;

import javafx.scene.control.Button;

public class CartTm {
    private String barcode;
    private String description;
    private double sellingPrice;
    private double discount;
    private double showPrice;
    private int qty;
    private double total;
    private Button button;

    public CartTm() {
    }

    public CartTm(String barcode, String description, double sellingPrice, double discount, double showPrice, int qty, double total, Button button) {
        this.barcode = barcode;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.showPrice = showPrice;
        this.qty = qty;
        this.total = total;
        this.button = button;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(double showPrice) {
        this.showPrice = showPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
