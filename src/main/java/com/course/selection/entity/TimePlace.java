package com.course.selection.entity;

public class TimePlace extends TimePlaceKey {
    private String place;
    
    private Integer num;
    
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }
    
    public Integer getNum(){
    	return num;
    }
    
    public void setNum(Integer num){
    	this.num = num;
    }
}