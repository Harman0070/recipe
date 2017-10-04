
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipetypename {

    @SerializedName("country_type")
    @Expose
    private String countryType;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("recipe_type_name")
    @Expose
    private String recipeTypeName;
    @SerializedName("category_parrent_id")
    @Expose
    private String categoryParrentId;
    @SerializedName("recipe_image")
    @Expose
    private String recipeImage;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Recipetypename() {
    }

    /**
     * 
     * @param countryName
     * @param categoryParrentId
     * @param recipeTypeName
     * @param categoryId
     * @param countryType
     * @param recipeImage
     */
    public Recipetypename(String countryType, String countryName, String categoryId, String recipeTypeName, String categoryParrentId, String recipeImage) {
        super();
        this.countryType = countryType;
        this.countryName = countryName;
        this.categoryId = categoryId;
        this.recipeTypeName = recipeTypeName;
        this.categoryParrentId = categoryParrentId;
        this.recipeImage = recipeImage;
    }

    public String getCountryType() {
        return countryType;
    }

    public void setCountryType(String countryType) {
        this.countryType = countryType;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getRecipeTypeName() {
        return recipeTypeName;
    }

    public void setRecipeTypeName(String recipeTypeName) {
        this.recipeTypeName = recipeTypeName;
    }

    public String getCategoryParrentId() {
        return categoryParrentId;
    }

    public void setCategoryParrentId(String categoryParrentId) {
        this.categoryParrentId = categoryParrentId;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

}
