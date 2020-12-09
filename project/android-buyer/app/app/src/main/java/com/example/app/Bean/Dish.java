package com.example.app.Bean;

public class Dish {
    private String name;
    private String price;
    private String info;
    private String id;
    private String shopId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", info='" + info + '\'' +
                ", id='" + id + '\'' +
                ", shopId='" + shopId + '\'' +
                '}';
    }
}
