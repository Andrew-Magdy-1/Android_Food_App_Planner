package com.example.food_app_planner.archistartcode.data.datasource.remote.ingrediantremote;

import com.example.food_app_planner.archistartcode.data.datasource.models.ingredient.IngredientResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IngredientService {
    @GET("list.php?i=list")
    Observable<IngredientResponse> getIngredients();
}