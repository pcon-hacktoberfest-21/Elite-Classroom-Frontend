package com.example.elite_classroom.Models.Retrofit_Models;

public class Auth_Responses {

    Integer success;
    String message ;
    String token ;

    public Auth_Responses(Integer success, String message, String token) {
        this.success = success;
        this.message = message;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
