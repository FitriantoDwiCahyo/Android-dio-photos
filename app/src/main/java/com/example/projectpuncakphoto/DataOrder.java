package com.example.projectpuncakphoto;


public class DataOrder {

    String date,price,fullname,location,classPackage,userCodeOrder;

    public DataOrder(){}

    public DataOrder(String date,String fullname,String location,String classPackage,String price,String userCodeOrder) {
        this.date = date;
        this.price = price;
        this.fullname = fullname;
        this.location = location;
        this.classPackage = classPackage;
        this.userCodeOrder = userCodeOrder;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(String classPackage) {
        this.classPackage = classPackage;
    }

    public String getUserCodeOrder() {
        return userCodeOrder;
    }

    public void setUserCodeOrder(String userCodeOrder) {
        this.classPackage = userCodeOrder;
    }
}

