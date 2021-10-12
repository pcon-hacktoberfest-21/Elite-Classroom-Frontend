package com.example.elite_classroom.Models.Retrofit_Models;

public class Cancel_Class_Request {

    String classCode;
    String date;
    String time;

    public Cancel_Class_Request(String classCode, String date, String time) {
        this.classCode = classCode;
        this.date = date;
        this.time = time;
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
}
