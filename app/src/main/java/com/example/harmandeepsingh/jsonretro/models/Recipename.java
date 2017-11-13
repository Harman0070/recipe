
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipename {

    @SerializedName("precipe_id")
    @Expose
    private String precipeId;
    @SerializedName("user_id")
    @Expose
    private String userId;
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
    public Recipename() {
    }

    /**
     * 
     * @param precipeVideoId
     * @param precipeName
     * @param precipeDetail
     * @param userId
     * @param precipeId
     * @param precipeImage
     * @param precipeParentId
     */
    public Recipename(String precipeId, String userId, String precipeName, String precipeImage, String precipeParentId, String precipeDetail, String precipeVideoId) {
        super();
        this.precipeId = precipeId;
        this.userId = userId;
        this.precipeName = precipeName;
        this.precipeImage = precipeImage;
        this.precipeParentId = precipeParentId;
        this.precipeDetail = precipeDetail;
        this.precipeVideoId = precipeVideoId;
    }

    public String getPrecipeId() {
        return precipeId;
    }

    public void setPrecipeId(String precipeId) {
        this.precipeId = precipeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
