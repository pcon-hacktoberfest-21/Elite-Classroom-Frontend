package com.example.elite_classroom.Models.Retrofit_Models;

public class Reschedule_Class {

    String classCode;
    String date;
    String old_time;
    String new_time;
    String description;
    String classLink;


    public Reschedule_Class(String classCode, String date, String old_time, String new_time, String description, String classLink) {
        this.classCode = classCode;
        this.date = date;
        this.old_time = old_time;
        this.new_time = new_time;
        this.description = description;
        this.classLink = classLink;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOld_time() {
        return old_time;
    }

    public void setOld_time(String old_time) {
        this.old_time = old_time;
    }

    public String getNew_time() {
        return new_time;
    }

    public void setNew_time(String new_time) {
        this.new_time = new_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassLink() {
        return classLink;
    }

    public void setClassLink(String classLink) {
        this.classLink = classLink;
    }
}
