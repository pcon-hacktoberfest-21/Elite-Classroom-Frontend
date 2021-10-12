package com.example.elite_classroom.Models.Retrofit_Models;

public class Schedule_Class_Request {

            String classCode ;
            String date ;
            String time;
            String description;
            String classLink;

    public Schedule_Class_Request(String classCode, String date, String time, String description, String classLink) {
        this.classCode = classCode;
        this.date = date;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
