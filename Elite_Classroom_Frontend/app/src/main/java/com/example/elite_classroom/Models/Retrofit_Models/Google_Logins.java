package com.example.elite_classroom.Models.Retrofit_Models;

public class Google_Logins {

    String name ;
    String email ;
    String google_token ;

    public Google_Logins(String name, String email, String google_token) {
        this.name = name;
        this.email = email;
        this.google_token = google_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogle_token() {
        return google_token;
    }

    public void setGoogle_token(String google_token) {
        this.google_token = google_token;
    }
}
