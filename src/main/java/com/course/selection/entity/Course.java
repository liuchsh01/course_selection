package com.course.selection.entity;

public class Course {
    private Integer courseId;

    private Integer collegeId;

    private Integer courseCode;

    private String courseName;

    private String majorChooser;

    private String teacher;

    private String credit;

    private Integer evaluationMode;

    private Integer majorNum;

    private Integer minorNum;

    private Integer creditType;

    private String comment;

    private Integer compulsoryElective;

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

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit == null ? null : credit.trim();
    }

    public Integer getEvaluationMode() {
        return evaluationMode;
    }

    public void setEvaluationMode(Integer evaluationMode) {
        this.evaluationMode = evaluationMode;
    }

    public Integer getMajorNum() {
        return majorNum;
    }

    public void setMajorNum(Integer majorNum) {
        this.majorNum = majorNum;
    }

    public Integer getMinorNum() {
        return minorNum;
    }

    public void setMinorNum(Integer minorNum) {
        this.minorNum = minorNum;
    }

    public Integer getCreditType() {
        return creditType;
    }

    public void setCreditType(Integer creditType) {
        this.creditType = creditType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getCompulsoryElective() {
        return compulsoryElective;
    }

    public void setCompulsoryElective(Integer compulsoryElective) {
        this.compulsoryElective = compulsoryElective;
    }
}