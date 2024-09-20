package com.dev.pos.dto.tm;

import javafx.scene.control.Button;

import java.util.Date;

public class OrdersTm {

    private int orderCode;
    private String customerName;
    private Date issuedDate;
    private double discount;
    private double totalCost;
    private Button showMore;
    private String userName;

    public OrdersTm() {
    }

    public OrdersTm(int orderCode, String customerName, Date issuedDate, double discount, double totalCost, Button showMore, String userName) {
        this.orderCode = orderCode;
        this.customerName = customerName;
        this.issuedDate = issuedDate;
        this.discount = discount;
        this.totalCost = totalCost;
        this.showMore = showMore;
        this.userName = userName;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Button getShowMore() {
        return showMore;
    }

    public void setShowMore(Button showMore) {
        this.showMore = showMore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
