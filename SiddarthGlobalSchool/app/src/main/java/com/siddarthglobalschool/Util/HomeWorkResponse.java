package com.siddarthglobalschool.Util;

import java.util.ArrayList;

public class HomeWorkResponse {
    private String  status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<HomeWorkList> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<HomeWorkList> message) {
        this.message = message;
    }

    private ArrayList<HomeWorkList>  message;


}
