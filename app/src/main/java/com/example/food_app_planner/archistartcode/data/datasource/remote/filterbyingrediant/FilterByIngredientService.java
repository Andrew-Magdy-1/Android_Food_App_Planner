package com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyingrediant;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilterByIngredientService {
    @GET("filter.php")
    Call<CategoryDetailsResponse> filterByIngredient(@Query("i") String ingredientName);
}