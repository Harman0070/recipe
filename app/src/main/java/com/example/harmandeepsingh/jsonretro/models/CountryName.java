
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryName {

    @SerializedName("country_type")
    @Expose
    private String countryType;
    @SerializedName("country_name")
    @Expose
    private String countryName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CountryName() {
    }

    /**
     * 
     * @param countryName
     * @param countryType
     */
    public CountryName(String countryType, String countryName) {
        super();
        this.countryType = countryType;
        this.countryName = countryName;
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

}
