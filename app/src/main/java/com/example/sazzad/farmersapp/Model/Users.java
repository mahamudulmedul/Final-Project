package com.example.sazzad.farmersapp.Model;

public class Users {

    private String image_url;
    private String name;
    private String phoneNo;
    private String road;
    private String city;
    private String district;
    private String type;
    private String email;
    private String RegType;
    public Users() {
    }

    public Users(String image_url, String name, String phoneNo, String road, String city, String district, String type, String email, String regType) {
        this.image_url = image_url;
        this.name = name;
        this.phoneNo = phoneNo;
        this.road = road;
        this.city = city;
        this.district = district;
        this.type = type;
        this.email = email;
        RegType = regType;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegType() {
        return RegType;
    }

    public void setRegType(String regType) {
        RegType = regType;
    }
}
