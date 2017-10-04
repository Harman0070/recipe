package com.example.harmandeepsingh.jsonretro.models;

/**
 * Created by Harmandeep singh on 8/23/2017.
 */

public class RandomCardItems {
   private String name;
    private String category;
    public int thumbnail;

    public RandomCardItems(){
    //default constructor
}
    public RandomCardItems(String name, String category, int thumbnail) {
        this.name = name;
        this.category = category;
        this.thumbnail = thumbnail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
