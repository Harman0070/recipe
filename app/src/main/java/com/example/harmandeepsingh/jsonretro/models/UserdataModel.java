
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserdataModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("user_data")
    @Expose
    private UserData userData;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserdataModel() {
    }

    /**
     * 
     * @param message
     * @param userData
     * @param success
     */
    public UserdataModel(Boolean success, UserData userData, String message) {
        super();
        this.success = success;
        this.userData = userData;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
