package com.dev.pos.dto;

public class ItemDto {

    private int orderCode;
    private int productCode;
    private String productName;
    private int qty;
    private double total;

    public ItemDto() {
    }

    public ItemDto(int orderCode, int productCode, String productName, int qty, double total) {
        this.orderCode = orderCode;
        this.productCode = productCode;
        this.productName = productName;
        this.qty = qty;
        this.total = total;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
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
}
