
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("timedetails")
    @Expose
    private Timedetails timedetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TimeModel() {
    }

    /**
     * 
     * @param message
     * @param success
     * @param timedetails
     */
    public TimeModel(Boolean success, String message, Timedetails timedetails) {
        super();
        this.success = success;
        this.message = message;
        this.timedetails = timedetails;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timedetails getTimedetails() {
        return timedetails;
    }

    public void setTimedetails(Timedetails timedetails) {
        this.timedetails = timedetails;
    }

}
