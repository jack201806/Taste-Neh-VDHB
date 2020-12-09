package com.example.app.Bean;

import java.util.List;

public class Order {
    private String storeHeader;
    private String storeName;
    private String orderState;
    private List<UserProduct> products;
    private String orderId;
    private String orderTime;
    private int orderNum;
    private double orderAmount;

    public String getStoreHeader() {
        return storeHeader;
    }

    public void setStoreHeader(String storeHeader) {
        this.storeHeader = storeHeader;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public List<UserProduct> getProducts() {
        return products;
    }

    public void setProducts(List<UserProduct> products) {
        this.products = products;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "storeHeader='" + storeHeader + '\'' +
                ", storeName='" + storeName + '\'' +
                ", orderState='" + orderState + '\'' +
                ", products=" + products +
                ", orderId='" + orderId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", orderNum=" + orderNum +
                ", orderAmount=" + orderAmount +
                '}';
    }
}
