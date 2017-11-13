
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Addcountrysearch2Model {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("recipeimage")
    @Expose
    private String recipeimage;
    @SerializedName("recipe_id")
    @Expose
    private int recipe_id;


    /**
     * No args constructor for use in serialization
     * 
     */
    public Addcountrysearch2Model() {
    }

    /**
     * 
     * @param message
     * @param success
     */
    public Addcountrysearch2Model(Boolean success, String message,int recipe_id) {
        super();
        this.success = success;
        this.message = message;
        this.recipe_id = recipe_id;
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

    public String getRecipeimage() {
        return recipeimage;
    }

    public void setRecipeimage(String recipeimage) {
        this.recipeimage = recipeimage;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

}
