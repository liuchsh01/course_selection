package com.course.selection.entity;

public class ChangList {
	private Integer out_userId;
	private Integer out_courseId;
	private Integer out_courseCode;
	private String out_courseName;
	private String out_time;
	private Integer in_courseId;
	private Integer in_courseCode;
	private String in_courseName;
	private String in_time;
	
	public Integer getOut_userId(){
	   	return out_userId;
	}
	
	public void setOut_userId(Integer out_userId){
		this.out_userId = out_userId;
	}
	
	public Integer getOut_courseId(){
	   	return out_courseId;
	}
	
	public void setOut_courseId(Integer out_courseId){
		this.out_courseId = out_courseId;
	}
	
	public Integer getOut_courseCode(){
	   	return out_courseCode;
	}
	
	public void setOut_courseCode(Integer out_courseCode){
		this.out_courseCode = out_courseCode;
	}
	
	public String getOut_courseName(){
	   	return out_courseName;
	}
	
	public void setOut_courseName(String out_courseName){
		this.out_courseName = out_courseName;
	}
	
	public String getOut_time(){
	   	return out_time;
	}
	
	public void setOut_time(String out_time){
		this.out_time = out_time;
	}
	
	public Integer getin_courseId(){
	   	return in_courseId;
	}
	
	public void setin_courseId(Integer in_courseId){
		this.in_courseId = in_courseId;
	}
	
	public Integer getin_courseCode(){
	   	return in_courseCode;
	}
	
	public void setin_courseCode(Integer in_courseCode){
		this.in_courseCode = in_courseCode;
	}
	
	public String getin_courseName(){
	   	return in_courseName;
	}
	
	public void setin_courseName(String in_courseName){
		this.in_courseName = in_courseName;
	}
	
	public String getin_time(){
	   	return in_time;
	}
	
	public void setin_time(String in_time){
		this.in_time = in_time;
	}
	
}
