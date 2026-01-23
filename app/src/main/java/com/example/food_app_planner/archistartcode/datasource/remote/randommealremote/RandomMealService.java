package com.example.food_app_planner.archistartcode.datasource.remote.randommealremote;

import com.example.food_app_planner.archistartcode.datasource.models.randommeal.RandomMealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomMealService {
    @GET("random.php")
     Call<RandomMealResponse> getRandomMeals();
}
