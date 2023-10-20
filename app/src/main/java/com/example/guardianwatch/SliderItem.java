package com.example.guardianwatch;

public class SliderItem {
    private String imageUrl;
    private String text;
    private int represent;  // 0: 대표 아님, 1: 대표

    public SliderItem(String imageUrl, String text, int represent) {
        this.imageUrl = imageUrl;
        this.text = text;
        this.represent=represent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getText() {
        return text;
    }

    public int getRepresent() {
        return represent;
    }
}
