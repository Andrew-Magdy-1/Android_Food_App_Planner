package com.example.food_app_planner.archistartcode.data.datasource.repositores.filterbyingredientrepo;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyingrediant.FilterByIngredientNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyingrediant.FilterByIngredientRemoteDataSource;

public class FilterByIngredientRepo {
    public FilterByIngredientRemoteDataSource filterByIngredientRemoteDataSource;

    public FilterByIngredientRepo(Context context) {
        filterByIngredientRemoteDataSource = new FilterByIngredientRemoteDataSource();
    }

    public void getIngredientMealsFromRepo(String ingredientName, FilterByIngredientNetworkResponse filterByIngredientNetworkResponse) {
        filterByIngredientRemoteDataSource.getIngredientMeals(ingredientName, filterByIngredientNetworkResponse);
    }
}