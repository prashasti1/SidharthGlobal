package com.siddarthglobalschool.Util;

import java.util.ArrayList;

public class EventResponse {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<EventData> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<EventData> message) {
        this.message = message;
    }

    private ArrayList<EventData> message;
}
