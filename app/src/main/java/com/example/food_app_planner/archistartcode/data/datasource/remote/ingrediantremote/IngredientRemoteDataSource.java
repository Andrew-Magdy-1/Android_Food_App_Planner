package com.example.food_app_planner.archistartcode.data.datasource.remote.ingrediantremote;

import android.util.Log;

import com.example.food_app_planner.archistartcode.data.datasource.models.ingredient.Ingredient;
import com.example.food_app_planner.archistartcode.data.datasource.models.ingredient.IngredientResponse;
import com.example.food_app_planner.archistartcode.network.Network;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientRemoteDataSource {
    private IngredientService ingredientService;

    public IngredientRemoteDataSource() {
        ingredientService = Network.getInstance().ingredientService;
    }

    public Observable<IngredientResponse> getIngredients( ) {
        return ingredientService.getIngredients();

    }
}