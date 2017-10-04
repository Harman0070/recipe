
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timedetails {

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
    @SerializedName("tbl_id")
    @Expose
    private String tblId;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("servings")
    @Expose
    private String servings;
    @SerializedName("likes")
    @Expose
    private String likes;
    @SerializedName("time_tbl_parent_id")
    @Expose
    private String timeTblParentId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Timedetails() {
    }

    /**
     * 
     * @param servings
     * @param time
     * @param precipeVideoId
     * @param timeTblParentId
     * @param precipeName
     * @param precipeDetail
     * @param likes
     * @param precipeId
     * @param precipeImage
     * @param tblId
     * @param precipeParentId
     */
    public Timedetails(String precipeId, String precipeName, String precipeImage, String precipeParentId, String precipeDetail, String precipeVideoId, String tblId, String time, String servings, String likes, String timeTblParentId) {
        super();
        this.precipeId = precipeId;
        this.precipeName = precipeName;
        this.precipeImage = precipeImage;
        this.precipeParentId = precipeParentId;
        this.precipeDetail = precipeDetail;
        this.precipeVideoId = precipeVideoId;
        this.tblId = tblId;
        this.time = time;
        this.servings = servings;
        this.likes = likes;
        this.timeTblParentId = timeTblParentId;
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

    public String getTblId() {
        return tblId;
    }

    public void setTblId(String tblId) {
        this.tblId = tblId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getTimeTblParentId() {
        return timeTblParentId;
    }

    public void setTimeTblParentId(String timeTblParentId) {
        this.timeTblParentId = timeTblParentId;
    }

}
