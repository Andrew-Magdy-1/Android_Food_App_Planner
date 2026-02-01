package com.example.food_app_planner.archistartcode.data.datasource.repositores.filterbyingredientrepo;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetailsResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyingrediant.FilterByIngredientRemoteDataSource;

import io.reactivex.rxjava3.core.Observable;

public class FilterByIngredientRepo {
    public FilterByIngredientRemoteDataSource filterByIngredientRemoteDataSource;

    public FilterByIngredientRepo(Context context) {
        filterByIngredientRemoteDataSource = new FilterByIngredientRemoteDataSource();
    }

    public Observable<CategoryDetailsResponse> getIngredientMealsFromRepo(String ingredientName  ) {
        return filterByIngredientRemoteDataSource.getIngredientMeals(ingredientName);
    }
}