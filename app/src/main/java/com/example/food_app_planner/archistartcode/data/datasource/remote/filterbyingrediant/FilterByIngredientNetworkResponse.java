package com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyingrediant;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;

import java.util.List;

public interface FilterByIngredientNetworkResponse {
    void onSunccess(List<CategoryDetails> mealList);
    void onFailure(String errorMessage);
}
