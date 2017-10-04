
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipePartModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("RecipeparticularData")
    @Expose
    private List<RecipeparticularDatum> recipeparticularData = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RecipePartModel() {
    }

    /**
     * 
     * @param message
     * @param recipeparticularData
     * @param success
     */
    public RecipePartModel(Boolean success, String message, List<RecipeparticularDatum> recipeparticularData) {
        super();
        this.success = success;
        this.message = message;
        this.recipeparticularData = recipeparticularData;
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

    public List<RecipeparticularDatum> getRecipeparticularData() {
        return recipeparticularData;
    }

    public void setRecipeparticularData(List<RecipeparticularDatum> recipeparticularData) {
        this.recipeparticularData = recipeparticularData;
    }

}
