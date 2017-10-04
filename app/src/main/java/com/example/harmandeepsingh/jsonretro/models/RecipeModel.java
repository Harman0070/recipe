
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Recipetypename")
    @Expose
    private List<Recipetypename> recipetypename = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RecipeModel() {
    }

    /**
     * 
     * @param message
     * @param recipetypename
     * @param success
     */
    public RecipeModel(Boolean success, String message, List<Recipetypename> recipetypename) {
        super();
        this.success = success;
        this.message = message;
        this.recipetypename = recipetypename;
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

    public List<Recipetypename> getRecipetypename() {
        return recipetypename;
    }

    public void setRecipetypename(List<Recipetypename> recipetypename) {
        this.recipetypename = recipetypename;
    }

}
