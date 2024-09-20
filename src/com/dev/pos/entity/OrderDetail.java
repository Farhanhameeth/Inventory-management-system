package com.dev.pos.entity;

import java.util.Date;

public class OrderDetail implements SuperEntity{

    private int code;
    private Date issuedDate;
    private double totalCost;
    private String customerEmail;
    private double discount;
    private String userEmail;

    public OrderDetail() {
    }

    public OrderDetail(int code, Date issuedDate, double totalCost, String customerEmail, double discount, String userEmail) {
        this.setCode(code);
        this.setIssuedDate(issuedDate);
        this.setTotalCost(totalCost);
        this.setCustomerEmail(customerEmail);
        this.setDiscount(discount);
        this.setUserEmail(userEmail);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
