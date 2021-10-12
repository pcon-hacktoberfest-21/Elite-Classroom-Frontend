package com.example.elite_classroom.Models.Recycler_Models;

public class ClassWork {
    String work_id;
    String class_code;
    String title;
    String description;
    int type;
    String attachment;
    String created_date;
    String due_date;
    String points;
    String owner_token;

    public ClassWork(String work_id, String class_code, String title, String description, int type, String attachment, String created_date, String due_date, String points, String owner_token) {
        this.work_id = work_id;
        this.class_code = class_code;
        this.title = title;
        this.description = description;
        this.type = type;
        this.attachment = attachment;
        this.created_date = created_date;
        this.due_date = due_date;
        this.points = points;
        this.owner_token = owner_token;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getClass_code() {
        return class_code;
    }

    public String getOwner_token() {
        return owner_token;
    }

    public void setOwner_token(String owner_token) {
        this.owner_token = owner_token;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;

    }
}
