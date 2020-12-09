package com.example.app.Bean;


import java.util.List;

public class Comment {
    private String userHeader;
    private String userName;
    private float userScore;
    private String orderTime;
    private String userComment;
    private List<Integer> commentImgs;

    public String getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getUserScore() {
        return userScore;
    }

    public void setUserScore(float userScore) {
        this.userScore = userScore;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public List<Integer> getCommentImgs() {
        return commentImgs;
    }

    public void setCommentImgs(List<Integer> commentImgs) {
        this.commentImgs = commentImgs;
    }
}
