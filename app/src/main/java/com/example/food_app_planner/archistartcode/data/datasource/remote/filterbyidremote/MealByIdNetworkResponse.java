package com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyidremote;

import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;

import java.util.List;

public interface MealByIdNetworkResponse {
   void onSuccess(List<MealById> mealByIdList);
   void onFailure(String errorMesage);
}
