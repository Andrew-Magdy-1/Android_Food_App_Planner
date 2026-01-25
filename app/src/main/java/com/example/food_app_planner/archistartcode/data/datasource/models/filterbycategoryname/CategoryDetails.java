package com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname;

public class CategoryDetails {
    private String strMeal;
    private String strMealThumb;
    private String idMeal;



    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }
    @Override
    public String toString() {
        return "CategoryDetails{" +
                "strMeal='" + strMeal + '\'' +
                ", strMealThumb='" + strMealThumb + '\'' +
                ", idMeal='" + idMeal + '\'' +
                '}';
    }
}
