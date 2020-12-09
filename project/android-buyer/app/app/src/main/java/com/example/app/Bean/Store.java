package com.example.app.Bean;

public class Store {
    private String name;
    private String image;
    public Store(String name,String image){
        this.name=name;
        this.image=image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
