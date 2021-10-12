package com.example.elite_classroom.Models.Recycler_Models;

public class Get_Classes_List {

    String class_code;
    String class_name;
    String owner_id;
    String number_of_participants;
    String owner_name;


    public Get_Classes_List(String class_code, String class_name, String owner_id, String number_of_participants, String owner_name) {
        this.class_code = class_code;
        this.class_name = class_name;
        this.owner_id = owner_id;
        this.number_of_participants = number_of_participants;
        this.owner_name = owner_name;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getNumber_of_participants() {
        return number_of_participants;
    }

    public void setNumber_of_participants(String number_of_participants) {
        this.number_of_participants = number_of_participants;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }
}
