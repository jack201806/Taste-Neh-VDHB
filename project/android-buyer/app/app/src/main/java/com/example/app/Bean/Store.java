package com.example.app.Bean;

public class Store {
    private double score;
    private int sale;
    private String name;
    private String storePhotoSrc;
    private String location;
    private int id;
    private int sellerId;

    public Store(double score, int sale, String name, String storePhotoSrc, String location, int id, int sellerId) {
        this.score = score;
        this.sale = sale;
        this.name = name;
        this.storePhotoSrc = storePhotoSrc;
        this.location = location;
        this.id = id;
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStorePhotoSrc() {
        return storePhotoSrc;
    }

    public void setStorePhotoSrc(String storePhotoSrc) {
        this.storePhotoSrc = storePhotoSrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Store{" +
                "score=" + score +
                ", sale=" + sale +
                ", name='" + name + '\'' +
                ", storePhotoSrc='" + storePhotoSrc + '\'' +
                ", location='" + location + '\'' +
                ", id=" + id +
                ", sellerId=" + sellerId +
                '}';
    }
}