package com.example.food_app_planner.archistartcode.data.datasource.remote.randommealremote;

import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMealResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomMealService {
    @GET("random.php")
    Observable<RandomMealResponse> getRandomMeals();
}
