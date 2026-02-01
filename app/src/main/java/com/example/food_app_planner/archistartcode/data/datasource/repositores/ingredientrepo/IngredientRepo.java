package com.example.food_app_planner.archistartcode.data.datasource.repositores.ingredientrepo;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.models.ingredient.IngredientResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.ingrediantremote.IngredientRemoteDataSource;

import io.reactivex.rxjava3.core.Observable;

public class IngredientRepo {
    public IngredientRemoteDataSource ingredientRemoteDataSource;

    public IngredientRepo(Context context) {
        ingredientRemoteDataSource = new IngredientRemoteDataSource();
    }

    public Observable<IngredientResponse> getIngredientsFromRepo() {
       return ingredientRemoteDataSource.getIngredients();
    }
}
