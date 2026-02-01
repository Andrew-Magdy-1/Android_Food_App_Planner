package com.example.food_app_planner.archistartcode.data.datasource.remote.randommealremote;

import android.util.Log;

import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMealResponse;
import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMeal;
import com.example.food_app_planner.archistartcode.network.Network;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RandomMealsRemoteDataSource {
    private RandomMealService randomMealService;
    public RandomMealsRemoteDataSource(){
        randomMealService= Network.getInstance().randomMealService;
    }
    public Observable<RandomMealResponse> getRandomMeal( ){
       return randomMealService.getRandomMeals();


    }

}
