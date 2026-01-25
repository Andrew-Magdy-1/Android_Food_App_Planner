package com.example.food_app_planner.archistartcode.presentation.homepage.view;

import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMeal;

import java.util.List;

public interface HomePageView {
    void onSuccessRandoms(List<RandomMeal> randomMealList);
    void onSuccessCategories(List<Category> categoryList);
    void onFailureRandoms(String errorMessage);
    void onFailureCategories(String errorMessage);
}
