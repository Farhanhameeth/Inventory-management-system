package com.dev.pos.dto.tm;

import javafx.scene.control.Button;

public class BatchTm {
    private String id;
    private int qty;
    private double buyingPrice;
    private String discount;
    private double showPrice;
    private double sellingPrice;
    private Button btnDelete;

    public BatchTm() {
    }

    public BatchTm(String id, int qty, double buyingPrice, String discount, double showPrice, double sellingPrice, Button btnDelete) {
        this.id = id;
        this.qty = qty;
        this.buyingPrice = buyingPrice;
        this.discount = discount;
        this.showPrice = showPrice;
        this.sellingPrice = sellingPrice;
        this.btnDelete = btnDelete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setBtnShowMore(int qty) {
        this.qty = qty;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String isDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public double getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(double showPrice) {
        this.showPrice = showPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }
}
