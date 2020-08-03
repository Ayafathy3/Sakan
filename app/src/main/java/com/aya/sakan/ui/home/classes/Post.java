package com.aya.sakan.ui.home.classes;

import java.util.Date;
import java.util.List;

public class Post {
    private Date date;
    String userId;
    List<String> imagesURL;
    private String title, roomsNum, bathroomsNum, area,price;

    public Post(Date date, String price, String userId, List<String> imagesURL, String title, String roomsNum, String bathroomsNum, String area) {
        this.date = date;
        this.price = price;
        this.userId = userId;
        this.imagesURL = imagesURL;
        this.title = title;
        this.roomsNum = roomsNum;
        this.bathroomsNum = bathroomsNum;
        this.area = area;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoomsNum() {
        return roomsNum;
    }

    public void setRoomsNum(String roomsNum) {
        this.roomsNum = roomsNum;
    }

    public String getBathroomsNum() {
        return bathroomsNum;
    }

    public void setBathroomsNum(String bathroomsNum) {
        this.bathroomsNum = bathroomsNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getImagesURL() {
        return imagesURL;
    }

    public void setImagesURL(List<String> imagesURL) {
        this.imagesURL = imagesURL;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
