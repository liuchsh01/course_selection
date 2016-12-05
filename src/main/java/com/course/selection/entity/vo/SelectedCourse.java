package com.course.selection.entity.vo;

import com.course.selection.entity.Course;

public class SelectedCourse extends Course {

	private Boolean compulsory;
	
    private Boolean nlisten;

	public Boolean getCompulsory() {
		return compulsory;
	}

	public void setCompulsory(Boolean compulsory) {
		this.compulsory = compulsory;
	}

	public Boolean getNlisten() {
		return nlisten;
	}

	public void setNlisten(Boolean nlisten) {
		this.nlisten = nlisten;
	}
}
