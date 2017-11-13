
package com.example.harmandeepsingh.jsonretro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("Firstname")
    @Expose
    private String firstname;
    @SerializedName("Lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("Password")
    @Expose
    private String password;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserData() {
    }

    /**
     * 
     * @param email
     * @param userid
     * @param lastname
     * @param firstname
     * @param password
     */
    public UserData(String userid, String firstname, String lastname, String email, String password) {
        super();
        this.userid = userid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
