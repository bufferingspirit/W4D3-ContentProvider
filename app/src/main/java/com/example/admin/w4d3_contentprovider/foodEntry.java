package com.example.admin.w4d3_contentprovider;

/**
 * Created by Admin on 8/23/2017.
 */

public class foodEntry {
    String name;
    String calories;
    String fat;
    String protein;
    String sodium;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getSodium() {
        return sodium;
    }

    public void setSodium(String sodium) {
        this.sodium = sodium;
    }

    public foodEntry(String name, String calories, String fat, String protein, String sodium) {

        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.sodium = sodium;
    }
}
