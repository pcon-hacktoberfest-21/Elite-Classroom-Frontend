package com.example.elite_classroom.Models.Retrofit_Models;

public class Upload_Response {

    String Location;

    public Upload_Response(String location) {
        Location = location;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
