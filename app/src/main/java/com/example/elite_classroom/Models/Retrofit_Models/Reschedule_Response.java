package com.example.elite_classroom.Models.Retrofit_Models;

public class Reschedule_Response {

    Integer success;
    String message;

    public Reschedule_Response(Integer success, String message) {
        this.success = success;
        this.message = message;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
