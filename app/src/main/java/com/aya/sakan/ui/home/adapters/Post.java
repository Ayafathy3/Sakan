package com.aya.sakan.ui.home.adapters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Post implements Serializable {
    public Date timestamp;
    ArrayList<String> images_url;
    private String area, desc, roomsNum, bathroomNum, location, userId, userName, userImg, phone,
            home_type, contractType, town, city;
    private Long price;
    private String postId;

    public Post() {
    }

    public Post(Date timestamp, ArrayList<String> images_url, String area, String desc,
                String roomsNum, String bathroomNum, String location, Long price,
                String userId, String home_type,
                String contractType, String town, String city, String postId) {
        this.timestamp = timestamp;
        this.images_url = images_url;
        this.area = area;
        this.desc = desc;
        this.roomsNum = roomsNum;
        this.bathroomNum = bathroomNum;
        this.location = location;
        this.price = price;
        this.userId = userId;
        this.home_type = home_type;
        this.contractType = contractType;
        this.town = town;
        this.city = city;
        this.postId = postId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUerImg() {
        return userImg;
    }

    public void setUerImg(String uerImg) {
        this.userImg = uerImg;
    }

    public Date getDate() {
        return timestamp;
    }

    public Long getPrice() {
        return price;
    }

    public String getTitle() {
        return desc;
    }

    public String getRoomsNum() {
        return roomsNum;
    }

    public String getBathroomsNum() {
        return bathroomNum;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<String> getImagesURL() {
        return images_url;
    }

    public String getArea() {
        return area;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDesc() {
        return desc;
    }

    public String getBathroomNum() {
        return bathroomNum;
    }

    public String getLocation() {
        return location;
    }

    public String getUserImg() {
        return userImg;
    }

    public String getHome_type() {
        return home_type;
    }

    public String getContractType() {
        return contractType;
    }

    public String getTown() {
        return town;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }
}
