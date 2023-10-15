package com.example.guardianwatch;

import java.io.Serializable;

public class ChildData implements Serializable {
    private String name;
    private String year;
    private String month;
    private String day;
    private String place;
    private String imageUri;
    private int gender;

    public ChildData(String name, String year, String month, String day, String place, String imageUri, int gender) {
        this.name = name;
        this.year=year;
        this.month = month;
        this.day=day;
        this.place = place;
        this.imageUri = imageUri;
        this.gender = gender;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public int getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }


    public String getPlace() {
        return place;
    }
    public String getImageUri() {
        return imageUri;
    }

}
