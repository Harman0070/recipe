
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StepsprepareModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("VideoID")
    @Expose
    private VideoID videoID;
    @SerializedName("Steps")
    @Expose
    private List<Step> steps = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public StepsprepareModel() {
    }

    /**
     * 
     * @param message
     * @param videoID
     * @param steps
     * @param success
     */
    public StepsprepareModel(Boolean success, String message, VideoID videoID, List<Step> steps) {
        super();
        this.success = success;
        this.message = message;
        this.videoID = videoID;
        this.steps = steps;
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

    public VideoID getVideoID() {
        return videoID;
    }

    public void setVideoID(VideoID videoID) {
        this.videoID = videoID;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

}
