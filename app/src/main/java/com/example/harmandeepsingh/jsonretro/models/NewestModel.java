
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewestModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("NewestRecipeName")
    @Expose
    private List<NewestRecipeName> newestRecipeName = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NewestModel() {
    }

    /**
     * 
     * @param message
     * @param newestRecipeName
     * @param success
     */
    public NewestModel(Boolean success, String message, List<NewestRecipeName> newestRecipeName) {
        super();
        this.success = success;
        this.message = message;
        this.newestRecipeName = newestRecipeName;
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

    public List<NewestRecipeName> getNewestRecipeName() {
        return newestRecipeName;
    }

    public void setNewestRecipeName(List<NewestRecipeName> newestRecipeName) {
        this.newestRecipeName = newestRecipeName;
    }

}
