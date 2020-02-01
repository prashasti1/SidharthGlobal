package com.siddarthglobalschool.Util;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MessageList getMessage() {
        return message;
    }

    public void setMessage(MessageList message) {
        this.message = message;
    }




    @SerializedName("message")
    private MessageList message;

}
