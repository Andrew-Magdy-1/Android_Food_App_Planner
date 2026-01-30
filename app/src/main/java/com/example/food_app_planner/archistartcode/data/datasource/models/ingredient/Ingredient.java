package com.example.food_app_planner.archistartcode.data.datasource.models.ingredient;

public class Ingredient {
    private String idIngredient;
    private String strIngredient;
    private String strDescription;
    private String strType;

    public String getIdIngredient() {
        return idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public String getStrType() {
        return strType;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "idIngredient='" + idIngredient + '\'' +
                ", strIngredient='" + strIngredient + '\'' +
                ", strDescription='" + strDescription + '\'' +
                ", strType='" + strType + '\'' +
                '}';
    }
}
