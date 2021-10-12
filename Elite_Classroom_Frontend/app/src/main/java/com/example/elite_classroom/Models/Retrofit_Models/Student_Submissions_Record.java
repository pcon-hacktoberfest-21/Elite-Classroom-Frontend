package com.example.elite_classroom.Models.Retrofit_Models;

public class Student_Submissions_Record {

     Integer submission_id;
             Integer user_id;
             Integer work_id;
             String work;
             String attachment;
             String submitted_on;
             String name;

    public Student_Submissions_Record(Integer submission_id, Integer user_id, Integer work_id, String work,
                                      String attachment, String submitted_on, String name) {
        this.submission_id = submission_id;
        this.user_id = user_id;
        this.work_id = work_id;
        this.work = work;
        this.attachment = attachment;
        this.submitted_on = submitted_on;
        this.name = name;
    }

    public Integer getSubmission_id() {
        return submission_id;
    }

    public void setSubmission_id(Integer submission_id) {
        this.submission_id = submission_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getWork_id() {
        return work_id;
    }

    public void setWork_id(Integer work_id) {
        this.work_id = work_id;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getSubmitted_on() {
        return submitted_on;
    }

    public void setSubmitted_on(String submitted_on) {
        this.submitted_on = submitted_on;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
