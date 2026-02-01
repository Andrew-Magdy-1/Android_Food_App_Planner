package com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyarearemote;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbyarea.AreaMealsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilterAreaService {
    @GET("filter.php")
    Observable<AreaMealsResponse> getAreaMeals(@Query("a") String area);

}
