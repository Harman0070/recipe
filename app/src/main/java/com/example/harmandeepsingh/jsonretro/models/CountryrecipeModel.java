
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryrecipeModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("CountryName")
    @Expose
    private List<CountryName> countryName = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CountryrecipeModel() {
    }

    /**
     * 
     * @param message
     * @param countryName
     * @param success
     */
    public CountryrecipeModel(Boolean success, String message, List<CountryName> countryName) {
        super();
        this.success = success;
        this.message = message;
        this.countryName = countryName;
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

    public List<CountryName> getCountryName() {
        return countryName;
    }

    public void setCountryName(List<CountryName> countryName) {
        this.countryName = countryName;
    }

}
