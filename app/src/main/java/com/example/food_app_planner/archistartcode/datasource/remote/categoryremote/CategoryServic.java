package com.example.food_app_planner.archistartcode.datasource.remote.categoryremote;

import com.example.food_app_planner.archistartcode.datasource.models.category.CategoryResonse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryServic {
    @GET("categories.php")
    Call<CategoryResonse> getCategories();
}
