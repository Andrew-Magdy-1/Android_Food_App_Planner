package com.example.food_app_planner.archistartcode.data.datasource.remote.ingrediantremote;

import com.example.food_app_planner.archistartcode.data.datasource.models.ingredient.IngredientResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IngredientService {
    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredients();
}