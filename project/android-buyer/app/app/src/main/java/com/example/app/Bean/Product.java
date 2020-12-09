package com.example.app.Bean;

public class Product {
    private String productId;
    private String productImg;
    private String productName;
    private String productIntro;
    private int productNum = 0;
    private double productPrice;
    private double orderAmount;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
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

    public String getProductIntro() {
        return productIntro;
    }

    public void setProductIntro(String productIntro) {
        this.productIntro = productIntro;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productImg='" + productImg + '\'' +
                ", productName='" + productName + '\'' +
                ", productIntro='" + productIntro + '\'' +
                ", productNum=" + productNum +
                ", productPrice=" + productPrice +
                ", orderAmount=" + orderAmount +
                '}';
    }
}
