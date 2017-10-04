
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ingre1Model {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Detail")
    @Expose
    private Detail detail;
    @SerializedName("IngredientData")
    @Expose
    private List<IngredientDatum> ingredientData = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Ingre1Model() {
    }

    /**
     * 
     * @param message
     * @param detail
     * @param ingredientData
     * @param success
     */
    public Ingre1Model(Boolean success, String message, Detail detail, List<IngredientDatum> ingredientData) {
        super();
        this.success = success;
        this.message = message;
        this.detail = detail;
        this.ingredientData = ingredientData;
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

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public List<IngredientDatum> getIngredientData() {
        return ingredientData;
    }

    public void setIngredientData(List<IngredientDatum> ingredientData) {
        this.ingredientData = ingredientData;
    }

}
