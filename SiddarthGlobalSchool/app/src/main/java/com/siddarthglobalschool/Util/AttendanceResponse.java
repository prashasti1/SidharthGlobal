package com.siddarthglobalschool.Util;

import java.util.ArrayList;

public class AttendanceResponse {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<AttenData> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<AttenData> message) {
        this.message = message;
    }

    private ArrayList<AttenData> message;
}
