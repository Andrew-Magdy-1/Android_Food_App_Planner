package com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyarearemote;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbyarea.AreaMealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilterAreaService {
    @GET("filter.php")
    Call<AreaMealsResponse> getAreaMeals(@Query("a") String area);

}
