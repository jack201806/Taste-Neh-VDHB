package com.example.app.Bean;

public class UserProduct {
    private String productId;
    private String storeId;
    private String productImg;
    private String productName;
    private double productPrice;
    private String productStandards;
    private int productNum;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductStandards() {
        return productStandards;
    }

    public void setProductStandards(String productStandards) {
        this.productStandards = productStandards;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    @Override
    public String toString() {
        return "UserProduct{" +
                "productId='" + productId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", productImg='" + productImg + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productStandards='" + productStandards + '\'' +
                ", productNum=" + productNum +
                '}';
    }
}
