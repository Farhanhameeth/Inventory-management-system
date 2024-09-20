package com.dev.pos.entity;

public class ItemDetail implements SuperEntity{

    private int order;
    private String batchCode;
    private int qty;
    private double discount;
    private double amount;

    public ItemDetail() {
    }

    public ItemDetail(int order, String batchCode, int qty, double discount, double amount) {
        this.setOrder(order);
        this.setBatchCode(batchCode);
        this.setQty(qty);
        this.setDiscount(discount);
        this.setAmount(amount);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
