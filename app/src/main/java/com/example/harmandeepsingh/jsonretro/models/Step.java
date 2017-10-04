
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {

    @SerializedName("step_id")
    @Expose
    private String stepId;
    @SerializedName("step_parent_id")
    @Expose
    private String stepParentId;
    @SerializedName("step_detail")
    @Expose
    private String stepDetail;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Step() {
    }

    /**
     * 
     * @param stepDetail
     * @param stepId
     * @param stepParentId
     */
    public Step(String stepId, String stepParentId, String stepDetail) {
        super();
        this.stepId = stepId;
        this.stepParentId = stepParentId;
        this.stepDetail = stepDetail;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public String getStepParentId() {
        return stepParentId;
    }

    public void setStepParentId(String stepParentId) {
        this.stepParentId = stepParentId;
    }

    public String getStepDetail() {
        return stepDetail;
    }

    public void setStepDetail(String stepDetail) {
        this.stepDetail = stepDetail;
    }

}
