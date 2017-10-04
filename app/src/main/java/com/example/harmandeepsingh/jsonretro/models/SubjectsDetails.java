
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectsDetails {



    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private String error ;

    public String getCode() {
        return error;
    }

    public void setCode(Integer code) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
