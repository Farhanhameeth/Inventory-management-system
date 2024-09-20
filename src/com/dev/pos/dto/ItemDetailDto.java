package com.dev.pos.dto;

public class ItemDetailDto {

    private String detailCode;
    private int qty;
    private double discount;
    private double amount;

    public ItemDetailDto() {
    }

    public ItemDetailDto(String detailCode, int qty, double discount, double amount) {
        this.setDetailCode(detailCode);
        this.setQty(qty);
        this.setDiscount(discount);
        this.setAmount(amount);
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
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
