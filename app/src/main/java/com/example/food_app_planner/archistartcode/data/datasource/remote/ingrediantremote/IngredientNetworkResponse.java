package com.example.food_app_planner.archistartcode.data.datasource.remote.ingrediantremote;

import com.example.food_app_planner.archistartcode.data.datasource.models.ingredient.Ingredient;

import java.util.List;

public interface IngredientNetworkResponse {
    void onSuccess(List<Ingredient> ingredientList);
    void onFailure(String errorMessage);
}