
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeparticularDatum {

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
    @SerializedName("precipe_id")
    @Expose
    private String precipeId;
    @SerializedName("precipe_name")
    @Expose
    private String precipeName;
    @SerializedName("precipe_image")
    @Expose
    private String precipeImage;
    @SerializedName("precipe_parent_id")
    @Expose
    private String precipeParentId;
    @SerializedName("precipe_detail")
    @Expose
    private String precipeDetail;
    @SerializedName("precipe_video_id")
    @Expose
    private String precipeVideoId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RecipeparticularDatum() {
    }

    /**
     * 
     * @param precipeVideoId
     * @param precipeName
     * @param categoryParrentId
     * @param recipeTypeName
     * @param precipeDetail
     * @param precipeId
     * @param categoryId
     * @param precipeImage
     * @param precipeParentId
     * @param recipeImage
     */
    public RecipeparticularDatum(String categoryId, String recipeTypeName, String categoryParrentId, String recipeImage, String precipeId, String precipeName, String precipeImage, String precipeParentId, String precipeDetail, String precipeVideoId) {
        super();
        this.categoryId = categoryId;
        this.recipeTypeName = recipeTypeName;
        this.categoryParrentId = categoryParrentId;
        this.recipeImage = recipeImage;
        this.precipeId = precipeId;
        this.precipeName = precipeName;
        this.precipeImage = precipeImage;
        this.precipeParentId = precipeParentId;
        this.precipeDetail = precipeDetail;
        this.precipeVideoId = precipeVideoId;
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

    public String getPrecipeId() {
        return precipeId;
    }

    public void setPrecipeId(String precipeId) {
        this.precipeId = precipeId;
    }

    public String getPrecipeName() {
        return precipeName;
    }

    public void setPrecipeName(String precipeName) {
        this.precipeName = precipeName;
    }

    public String getPrecipeImage() {
        return precipeImage;
    }

    public void setPrecipeImage(String precipeImage) {
        this.precipeImage = precipeImage;
    }

    public String getPrecipeParentId() {
        return precipeParentId;
    }

    public void setPrecipeParentId(String precipeParentId) {
        this.precipeParentId = precipeParentId;
    }

    public String getPrecipeDetail() {
        return precipeDetail;
    }

    public void setPrecipeDetail(String precipeDetail) {
        this.precipeDetail = precipeDetail;
    }

    public String getPrecipeVideoId() {
        return precipeVideoId;
    }

    public void setPrecipeVideoId(String precipeVideoId) {
        this.precipeVideoId = precipeVideoId;
    }

}
