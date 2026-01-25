package com.example.food_app_planner.archistartcode.data.datasource.repositores.filtermealbyidrepo;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyidremote.MealByIdNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyidremote.MealByIdRemoteDataSource;

public class MealByIdRepo {
   private MealByIdRemoteDataSource mealByIdRemoteDataSource;
   public MealByIdRepo(Context context){
       mealByIdRemoteDataSource=new MealByIdRemoteDataSource();
   }
   public void getMealByIdFromRepo(String id, MealByIdNetworkResponse mealByIdNetworkResponse){
       mealByIdRemoteDataSource.getMealById(id,mealByIdNetworkResponse);
   }
}
