package com.example.app.vo;

public class RightVo {
    private int id;
    private String title;
    private String productImg;
    private String productName;
    private String productIntro;
    private int productSale;
    private double productPrice;
    private int productNum = 0;
    private int itemType;
    private int fakePosition;   //Title左侧位置
    private double orderAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getProductSale() {
        return productSale;
    }

    public void setProductSale(int productSale) {
        this.productSale = productSale;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getFakePosition() {
        return fakePosition;
    }

    public void setFakePosition(int fakePosition) {
        this.fakePosition = fakePosition;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
    public String toString() {
        return "RightVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", productImg='" + productImg + '\'' +
                ", productName='" + productName + '\'' +
                ", productIntro='" + productIntro + '\'' +
                ", productSale=" + productSale +
                ", productPrice=" + productPrice +
                ", productNum=" + productNum +
                ", itemType=" + itemType +
                ", fakePosition=" + fakePosition +
                '}';
    }
}
