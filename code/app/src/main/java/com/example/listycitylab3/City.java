package com.example.listycitylab3;

import java.io.Serializable;

public class City implements Serializable { // Following the given hint in the Lab 3 Exercise
    private String city;
    private String province;

    public City(String name, String province) {
        this.city = name;
        this.province = province;
    }

    public String getCityName() {
        return city;
    }

    public String getProvinceName() {
        return province;
    }

    // Creating setters for the city and province names
    public void setCityName(String cityName) {
        this.city = cityName;
    }

    public void setProvinceName(String provinceName) {
        this.province = provinceName;
    }

}
