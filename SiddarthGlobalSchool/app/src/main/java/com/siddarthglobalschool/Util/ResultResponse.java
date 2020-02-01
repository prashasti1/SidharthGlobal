package com.siddarthglobalschool.Util;

import com.siddarthglobalschool.Fragnemt.Result;

import java.util.ArrayList;

public class ResultResponse {
    private String status;

     private String error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ResultData> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<ResultData> message) {
        this.message = message;
    }

    private ArrayList<ResultData> message;
}
