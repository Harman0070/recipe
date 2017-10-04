
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Noofrowsmodel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Noofrows")
    @Expose
    private Integer noofrows;
    @SerializedName("totalLikes")
    @Expose
    private Integer totalikes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Noofrowsmodel() {
    }

    /**
     * 
     * @param message
     * @param noofrows
     * @param success
     */
    public Noofrowsmodel(Boolean success, String message, Integer noofrows) {
        super();
        this.success = success;
        this.message = message;
        this.noofrows = noofrows;
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

    public Integer getNoofrows() {
        return noofrows;
    }

    public void setNoofrows(Integer noofrows) {
        this.noofrows = noofrows;
    }

    public Integer getTotalikes() {
        return totalikes;
    }

    public void setTotalikes(Integer totalikes) {
        this.totalikes = totalikes;
    }
}
