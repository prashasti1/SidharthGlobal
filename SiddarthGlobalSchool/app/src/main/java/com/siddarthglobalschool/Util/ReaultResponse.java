package com.siddarthglobalschool.Util;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReaultResponse { private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public ArrayList<MessageList> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<MessageList> message) {
        this.message = message;
    }

    private ArrayList<MessageList> message;

}
