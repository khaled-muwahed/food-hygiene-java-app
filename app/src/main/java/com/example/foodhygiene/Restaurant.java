package com.example.foodhygiene;

import android.content.pm.PackageManager;

public class Restaurant {

    private String name;
    private int hygieneRate;
    private String address;
    private String postCode;



    public Restaurant(){
        this.name= name;
        this.hygieneRate= hygieneRate;
        this.address= address;
        this.postCode=postCode;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPostCode() {
        return postCode;
    }
    public int getHygieneRate() {
        return hygieneRate;
    }

    public void setHygieneRate(int hygieneRate) {
        this.hygieneRate = hygieneRate;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
