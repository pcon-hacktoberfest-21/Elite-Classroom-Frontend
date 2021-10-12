package com.example.elite_classroom.Models.Retrofit_Models;

import android.content.Intent;

public class Submission_Response {

            Integer fieldCount;
            Integer affectedRows;
            Integer insertId;
            Integer serverStatus;
            Integer warningCount;
            String message;
            Boolean protocol41;
            Integer changedRows;


    public Submission_Response(Integer fieldCount, Integer affectedRows, Integer insertId,
                               Integer serverStatus, Integer warningCount, String message,
                               Boolean protocol41, Integer changedRows) {
        this.fieldCount = fieldCount;
        this.affectedRows = affectedRows;
        this.insertId = insertId;
        this.serverStatus = serverStatus;
        this.warningCount = warningCount;
        this.message = message;
        this.protocol41 = protocol41;
        this.changedRows = changedRows;
    }


    public Integer getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(Integer fieldCount) {
        this.fieldCount = fieldCount;
    }

    public Integer getAffectedRows() {
        return affectedRows;
    }

    public void setAffectedRows(Integer affectedRows) {
        this.affectedRows = affectedRows;
    }

    public Integer getInsertId() {
        return insertId;
    }

    public void setInsertId(Integer insertId) {
        this.insertId = insertId;
    }

    public Integer getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(Integer serverStatus) {
        this.serverStatus = serverStatus;
    }

    public Integer getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(Integer warningCount) {
        this.warningCount = warningCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getProtocol41() {
        return protocol41;
    }

    public void setProtocol41(Boolean protocol41) {
        this.protocol41 = protocol41;
    }

    public Integer getChangedRows() {
        return changedRows;
    }

    public void setChangedRows(Integer changedRows) {
        this.changedRows = changedRows;
    }
}
