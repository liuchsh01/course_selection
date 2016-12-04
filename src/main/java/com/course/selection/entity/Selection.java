package com.course.selection.entity;

public class Selection extends SelectionKey {
    private Boolean nlisten;

    private Boolean compulsory;

	public Boolean getNlisten() {
		return nlisten;
	}

	public void setNlisten(Boolean nlisten) {
		this.nlisten = nlisten;
	}

	public Boolean getCompulsory() {
		return compulsory;
	}

	public void setCompulsory(Boolean compulsory) {
		this.compulsory = compulsory;
	}
}