package com.course.selection.entity;

public class TimePlace extends TimePlaceKey {
    private String place;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }
}