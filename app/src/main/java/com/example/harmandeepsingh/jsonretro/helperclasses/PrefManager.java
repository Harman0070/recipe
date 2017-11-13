package com.example.harmandeepsingh.jsonretro.helperclasses;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Harmandeep singh on 8/11/2017.
 */


public class PrefManager {
    Context context;

    public PrefManager(Context context) {
        this.context = context;
    }

    public void saveLoginDetails(String email,String userid){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",email);
        editor.putString("userid", userid);
        editor.putBoolean("alreadyloggedin",true);
        editor.commit();

    }
    public String getUserEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }
    public String getUserId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userid", "");

    }

}
