package com.example.guardianwatch;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChildData implements Serializable {
//    @SerializedName("name")
    private String name;
//    @SerializedName("year")
    private String year;
//    @SerializedName("month")
    private String month;
//    @SerializedName("day")
    private String day;
//    @SerializedName("place")
    private String place;
//    @SerializedName("image")
    private String image;
//    @SerializedName("gender")
    private int gender;
//    @SerializedName("represent")
    private int represent;

    public ChildData(String name, String year, String month, String day, String place, String image, int gender,int represent) {
        this.name = name;
        this.year=year;
        this.month = month;
        this.day=day;
        this.place = place;
        this.image = image;
        this.gender = gender;
        this.represent=represent;
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
        return image;
    }

    public int getRepresent() {
        return represent;
    }
}
