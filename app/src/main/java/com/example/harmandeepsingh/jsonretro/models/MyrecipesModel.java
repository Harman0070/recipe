
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyrecipesModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Mydata")
    @Expose
    private List<Mydatum> mydata = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MyrecipesModel() {
    }

    /**
     * 
     * @param message
     * @param success
     * @param mydata
     */
    public MyrecipesModel(Boolean success, String message, List<Mydatum> mydata) {
        super();
        this.success = success;
        this.message = message;
        this.mydata = mydata;
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

    public List<Mydatum> getMydata() {
        return mydata;
    }

    public void setMydata(List<Mydatum> mydata) {
        this.mydata = mydata;
    }

}
