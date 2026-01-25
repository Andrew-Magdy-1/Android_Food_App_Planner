package com.example.food_app_planner.archistartcode.data.datasource.remote.randommealremote;

import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMeal;

import java.util.List;

public interface RandomMealNetworkResponse {
     void onSuccess(List<RandomMeal> randomMealList);
     void onFailure(String errorMessage);
}
