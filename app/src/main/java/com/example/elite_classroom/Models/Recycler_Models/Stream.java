package com.example.elite_classroom.Models.Recycler_Models;

public class Stream {
    String notes_id;
    String class_code;
    String attachment_id;
    String posted_on;
    String title;
    String description;
    String owner_token;

    public Stream(String notes_id, String class_code, String attachment_id, String posted_on, String title, String description, String owner_token) {
        this.notes_id = notes_id;
        this.class_code = class_code;
        this.attachment_id = attachment_id;
        this.posted_on = posted_on;
        this.title = title;
        this.description = description;
        this.owner_token = owner_token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes_id() {
        return notes_id;
    }

    public void setNotes_id(String notes_id) {
        this.notes_id = notes_id;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getAttachment_id() {
        return attachment_id;
    }

    public void setAttachment_id(String attachment_id) {
        this.attachment_id = attachment_id;
    }

    public String getPosted_on() {
        return posted_on;
    }

    public void setPosted_on(String posted_on) {
        this.posted_on = posted_on;
    }

    public String getOwner_token() {
        return owner_token;
    }

    public void setOwner_token(String owner_token) {
        this.owner_token = owner_token;
    }
}
