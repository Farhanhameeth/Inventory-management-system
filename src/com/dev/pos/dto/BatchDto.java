package com.dev.pos.dto;

public class BatchDto {
    private String code;
    private String barcode;
    private int qtyOnHand;
    private double sellingPrice;
    private boolean discount;
    private double showPrice;
    private double buyingPrice;
    private int productCode;

    public BatchDto() {
    }

    public BatchDto(String code, String barcode, int qtyOnHand, double sellingPrice, boolean discount, double showPrice,
                    double buyingPrice, int productCode) {
        this.code = code;
        this.barcode = barcode;
        this.qtyOnHand = qtyOnHand;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.showPrice = showPrice;
        this.buyingPrice = buyingPrice;
        this.productCode = productCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public double getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(double showPrice) {
        this.showPrice = showPrice;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }
}

