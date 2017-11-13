
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchrecipeModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("recipename")
    @Expose
    private List<Recipename> recipename = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SearchrecipeModel() {
    }

    /**
     * 
     * @param message
     * @param recipename
     * @param success
     */
    public SearchrecipeModel(Boolean success, String message, List<Recipename> recipename) {
        super();
        this.success = success;
        this.message = message;
        this.recipename = recipename;
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

    public List<Recipename> getRecipename() {
        return recipename;
    }

    public void setRecipename(List<Recipename> recipename) {
        this.recipename = recipename;
    }

}
