package com.course.selection.entity;

public class Course {
    private Integer courseId;

    private Integer collegeId;

    private Integer courseCode;

    private String courseName;

    private String majorChooser;

    private String teacher;

    private Double credit;

    private Boolean evaluationMode;

    private Integer limitNum;

    private Integer totalNum;

    private String weekBlock;

    private Boolean creditType;

    private String comment;

    private Long version;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public Integer getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(Integer courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public String getMajorChooser() {
        return majorChooser;
    }

    public void setMajorChooser(String majorChooser) {
        this.majorChooser = majorChooser == null ? null : majorChooser.trim();
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher == null ? null : teacher.trim();
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit == null ? 0.0 : credit;
    }

    public Boolean getEvaluationMode() {
        return evaluationMode;
    }

    public void setEvaluationMode(Boolean evaluationMode) {
        this.evaluationMode = evaluationMode;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getWeekBlock() {
		return weekBlock;
	}

	public void setWeekBlock(String weekBlock) {
		this.weekBlock = weekBlock;
	}

	public Boolean getCreditType() {
        return creditType;
    }

    public void setCreditType(Boolean creditType) {
        this.creditType = creditType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}