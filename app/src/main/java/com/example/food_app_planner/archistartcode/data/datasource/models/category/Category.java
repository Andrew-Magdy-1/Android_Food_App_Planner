package com.example.food_app_planner.archistartcode.data.datasource.models.category;

public class Category {
    private String idCategory;
    private String strCategory;
    private String strCategoryThumb;
    private String strCategoryDescription;

    public String getIdCategory() {
        return idCategory;
    }

    public String getStrCategoryDescription() {
        return strCategoryDescription;
    }

    public String getStrCategoryThumb() {
        return strCategoryThumb;
    }

    public String getStrCategory() {
        return strCategory;
    }

    @Override
    public String toString() {
        return "Category{" +
                "idCategory='" + idCategory + '\'' +
                ", strCategory='" + strCategory + '\'' +
                ", strCategoryThumb='" + strCategoryThumb + '\'' +
                ", strCategoryDescription='" + strCategoryDescription + '\'' +
                '}';
    }
}
