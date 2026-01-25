package com.example.food_app_planner.archistartcode.data.datasource.models.countries;

public class Country {
    private String strArea;

    @Override
    public String toString() {
        return "Country{" +
                "strArea='" + strArea + '\'' +
                '}';
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }
}
