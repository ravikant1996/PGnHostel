package com.example.testlogin.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomResponse {

    @SerializedName("result")
    @Expose
    private List<Room> result = null;

    public List<Room> getResult() {
        return result;
    }


    public void setResult(List<Room> result) {
        this.result = result;
    }

}