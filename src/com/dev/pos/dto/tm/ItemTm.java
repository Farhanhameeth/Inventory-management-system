package com.dev.pos.dto.tm;

public class ItemTm {
    private int productCode;
    private String productName;
    private String batchCode;
    private int quantity;
    private double discount;
    private double totalAmount;

    public ItemTm() {
    }

    public ItemTm(int productCode, String productName, String batchCode, int quantity, double discount, double totalAmount) {
        this.productCode = productCode;
        this.productName = productName;
        this.batchCode = batchCode;
        this.quantity = quantity;
        this.discount = discount;
        this.totalAmount = totalAmount;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
