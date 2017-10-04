package com.example.harmandeepsingh.jsonretro.models;

/**
 * Created by Harmandeep singh on 8/24/2017.
 */

public class CategoryRecipesCardItems {
    private String name;
    private String category;
    private int thumbnail;
     public  CategoryRecipesCardItems(){

    }

    public CategoryRecipesCardItems(String name, String category, int thumbnail) {
        this.name = name;
        this.category = category;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
