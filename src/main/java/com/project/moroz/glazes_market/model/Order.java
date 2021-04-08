package com.project.moroz.glazes_market.model;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private Date orderDate;
    private int orderNum;
    private double amount;
    private String userName;
    private List<OrderItemInfo> details;

    public Order() {
    }

    public Order(int id, Date orderDate, int orderNum, double amount, String userName) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderNum = orderNum;
        this.amount = amount;
        this.userName = userName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<OrderItemInfo> getDetails() {
        return details;
    }

    public void setDetails(List<OrderItemInfo> details) {
        this.details = details;
    }
}
