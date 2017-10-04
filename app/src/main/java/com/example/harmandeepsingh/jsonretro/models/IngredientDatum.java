
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IngredientDatum {

    @SerializedName("ing_id")
    @Expose
    private String ingId;
    @SerializedName("ing_parent_id")
    @Expose
    private String ingParentId;
    @SerializedName("ing_detail")
    @Expose
    private String ingDetail;
    @SerializedName("ing_amt")
    @Expose
    private String ingAmt;

    /**
     * No args constructor for use in serialization
     * 
     */
    public IngredientDatum() {
    }

    /**
     * 
     * @param ingAmt
     * @param ingParentId
     * @param ingId
     * @param ingDetail
     */
    public IngredientDatum(String ingId, String ingParentId, String ingDetail, String ingAmt) {
        super();
        this.ingId = ingId;
        this.ingParentId = ingParentId;
        this.ingDetail = ingDetail;
        this.ingAmt = ingAmt;
    }

    public String getIngId() {
        return ingId;
    }

    public void setIngId(String ingId) {
        this.ingId = ingId;
    }

    public String getIngParentId() {
        return ingParentId;
    }

    public void setIngParentId(String ingParentId) {
        this.ingParentId = ingParentId;
    }

    public String getIngDetail() {
        return ingDetail;
    }

    public void setIngDetail(String ingDetail) {
        this.ingDetail = ingDetail;
    }

    public String getIngAmt() {
        return ingAmt;
    }

    public void setIngAmt(String ingAmt) {
        this.ingAmt = ingAmt;
    }

}
