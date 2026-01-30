package com.example.food_app_planner.archistartcode.data.datasource.repositores.ingredientrepo;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.remote.ingrediantremote.IngredientNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.ingrediantremote.IngredientRemoteDataSource;

public class IngredientRepo {
    public IngredientRemoteDataSource ingredientRemoteDataSource;

    public IngredientRepo(Context context) {
        ingredientRemoteDataSource = new IngredientRemoteDataSource();
    }

    public void getIngredientsFromRepo(IngredientNetworkResponse ingredientNetworkResponse) {
        ingredientRemoteDataSource.getIngredients(ingredientNetworkResponse);
    }
}
