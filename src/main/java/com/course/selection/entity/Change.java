package com.course.selection.entity;

public class Change {
	private Integer userId;
	private Integer inCourseId;
	private Integer outCourseId;

	public Integer getUserId(){
		return userId;
	}
	
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	
	public Integer getInCourseId(){
		return inCourseId;
	}
	
	public void setInCourseId(Integer inCourseId){
		this.inCourseId = inCourseId;
	}
	
	public Integer getOutCourseId(){
		return outCourseId;
	}
	
	public void setOutCourseId(Integer outCourseId){
		this.outCourseId = outCourseId;
	}
}
