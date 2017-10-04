
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddcategoryModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("SpinnerCategoryName")
    @Expose
    private List<SpinnerCategoryName> spinnerCategoryName = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddcategoryModel() {
    }

    /**
     * 
     * @param message
     * @param spinnerCategoryName
     * @param success
     */
    public AddcategoryModel(Boolean success, String message, List<SpinnerCategoryName> spinnerCategoryName) {
        super();
        this.success = success;
        this.message = message;
        this.spinnerCategoryName = spinnerCategoryName;
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

    public List<SpinnerCategoryName> getSpinnerCategoryName() {
        return spinnerCategoryName;
    }

    public void setSpinnerCategoryName(List<SpinnerCategoryName> spinnerCategoryName) {
        this.spinnerCategoryName = spinnerCategoryName;
    }

}
