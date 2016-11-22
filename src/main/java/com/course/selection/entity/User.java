package com.course.selection.entity;

public class User {
    private Integer userId;

    private Integer studno;

    private String password;

    private String name;

    private String sex;

    private Integer collegeId;

    private String major;

    private Integer clazz;

    private Integer grade;

    private Integer limitCredit;

    private Integer selectedCredit;

    private Integer nlistenCredit;

    private String eMail;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStudno() {
        return studno;
    }

    public void setStudno(Integer studno) {
        this.studno = studno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public Integer getClazz() {
        return clazz;
    }

    public void setClazz(Integer clazz) {
        this.clazz = clazz;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getLimitCredit() {
        return limitCredit;
    }

    public void setLimitCredit(Integer limitCredit) {
        this.limitCredit = limitCredit;
    }

    public Integer getSelectedCredit() {
        return selectedCredit;
    }

    public void setSelectedCredit(Integer selectedCredit) {
        this.selectedCredit = selectedCredit;
    }

    public Integer getNlistenCredit() {
        return nlistenCredit;
    }

    public void setNlistenCredit(Integer nlistenCredit) {
        this.nlistenCredit = nlistenCredit;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail == null ? null : eMail.trim();
    }
}