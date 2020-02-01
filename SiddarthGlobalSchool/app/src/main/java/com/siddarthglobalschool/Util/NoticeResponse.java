package com.siddarthglobalschool.Util;

import java.util.ArrayList;

public class NoticeResponse {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<NoticeData> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<NoticeData> message) {
        this.message = message;
    }

    private ArrayList<NoticeData> message;
}
