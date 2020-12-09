package com.example.app.vo;

public class LeftVo {
    private int id;
    private String title;
    private boolean isSelected;

    public LeftVo(int id, String title) {
        this.id = id;
        this.title = title;
        isSelected = false;
    }

    public LeftVo(int id, String title, boolean isSelected) {
        this.id = id;
        this.title = title;
        this.isSelected = isSelected;
    }

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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
