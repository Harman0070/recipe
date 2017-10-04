package com.example.harmandeepsingh.jsonretro.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectData {

    @SerializedName("CLASS_SUBLIST_ID")
    @Expose
    private String cLASSSUBLISTID;
    @SerializedName("CLASS_ID")
    @Expose
    private String cLASSID;
    @SerializedName("TEACHER_ID")
    @Expose
    private String tEACHERID;
    @SerializedName("SUBJECT_ID")
    @Expose
    private String sUBJECTID;
    @SerializedName("CREATED_ON")
    @Expose
    private String cREATEDON;
    @SerializedName("SUBJECT_NAME")
    @Expose
    private String sUBJECTNAME;
    @SerializedName("FNAME")
    @Expose
    private String fNAME;
    @SerializedName("MNAME")
    @Expose
    private String mNAME;
    @SerializedName("LNAME")
    @Expose
    private String lNAME;
    @SerializedName("TEACHER_TYPE")
    @Expose
    private String tEACHERTYPE;
    @SerializedName("CONTACT_NUMBER")
    @Expose
    private String cONTACTNUMBER;
    @SerializedName("EMAIL")
    @Expose
    private String eMAIL;
    @SerializedName("PASSWORD")
    @Expose
    private String pASSWORD;
    @SerializedName("IMAGE_ID")
    @Expose
    private String iMAGEID;

    public String getCLASSSUBLISTID() {
        return cLASSSUBLISTID;
    }

    public void setCLASSSUBLISTID(String cLASSSUBLISTID) {
        this.cLASSSUBLISTID = cLASSSUBLISTID;
    }

    public String getCLASSID() {
        return cLASSID;
    }

    public void setCLASSID(String cLASSID) {
        this.cLASSID = cLASSID;
    }

    public String getTEACHERID() {
        return tEACHERID;
    }

    public void setTEACHERID(String tEACHERID) {
        this.tEACHERID = tEACHERID;
    }

    public String getSUBJECTID() {
        return sUBJECTID;
    }

    public void setSUBJECTID(String sUBJECTID) {
        this.sUBJECTID = sUBJECTID;
    }

    public String getCREATEDON() {
        return cREATEDON;
    }

    public void setCREATEDON(String cREATEDON) {
        this.cREATEDON = cREATEDON;
    }

    public String getSUBJECTNAME() {
        return sUBJECTNAME;
    }

    public void setSUBJECTNAME(String sUBJECTNAME) {
        this.sUBJECTNAME = sUBJECTNAME;
    }

    public String getFNAME() {
        return fNAME;
    }

    public void setFNAME(String fNAME) {
        this.fNAME = fNAME;
    }

    public String getMNAME() {
        return mNAME;
    }

    public void setMNAME(String mNAME) {
        this.mNAME = mNAME;
    }

    public String getLNAME() {
        return lNAME;
    }

    public void setLNAME(String lNAME) {
        this.lNAME = lNAME;
    }

    public String getTEACHERTYPE() {
        return tEACHERTYPE;
    }

    public void setTEACHERTYPE(String tEACHERTYPE) {
        this.tEACHERTYPE = tEACHERTYPE;
    }

    public String getCONTACTNUMBER() {
        return cONTACTNUMBER;
    }

    public void setCONTACTNUMBER(String cONTACTNUMBER) {
        this.cONTACTNUMBER = cONTACTNUMBER;
    }

    public String getEMAIL() {
        return eMAIL;
    }

    public void setEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
    }

    public String getPASSWORD() {
        return pASSWORD;
    }

    public void setPASSWORD(String pASSWORD) {
        this.pASSWORD = pASSWORD;
    }

    public String getIMAGEID() {
        return iMAGEID;
    }

    public void setIMAGEID(String iMAGEID) {
        this.iMAGEID = iMAGEID;
    }

}
