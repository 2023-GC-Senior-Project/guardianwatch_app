package com.example.guardianwatch;

public class ChildData {
    private String name;
    private String birthDate;
    private String place;
    private int imageResId;
    public ChildData(String name, String birthDate, String place, int imageResId) {
        this.name = name;
        this.birthDate = birthDate;
        this.place = place;
        this.imageResId = imageResId;

    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPlace() {
        return place;
    }
    public int getImageResId() {
        return imageResId;
    }
}
