
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoID {

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
    public VideoID() {
    }

    /**
     * 
     * @param precipeVideoId
     * @param precipeName
     * @param precipeDetail
     * @param precipeId
     * @param precipeImage
     * @param precipeParentId
     */
    public VideoID(String precipeId, String precipeName, String precipeImage, String precipeParentId, String precipeDetail, String precipeVideoId) {
        super();
        this.precipeId = precipeId;
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
