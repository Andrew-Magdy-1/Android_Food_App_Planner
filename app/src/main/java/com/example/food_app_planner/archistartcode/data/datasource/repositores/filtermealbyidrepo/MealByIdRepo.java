package com.example.food_app_planner.archistartcode.data.datasource.repositores.filtermealbyidrepo;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.local.mealtofavourite.MealLocalDataSource;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyidremote.MealByIdNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyidremote.MealByIdRemoteDataSource;

import java.util.List;

public class MealByIdRepo {
   private MealByIdRemoteDataSource mealByIdRemoteDataSource;
   private MealLocalDataSource mealLocalDataSource;
   public MealByIdRepo(Context context){
       mealByIdRemoteDataSource=new MealByIdRemoteDataSource();
       mealLocalDataSource=new MealLocalDataSource(context);
   }
   public void getMealByIdFromRepo(String id, MealByIdNetworkResponse mealByIdNetworkResponse){
       mealByIdRemoteDataSource.getMealById(id,mealByIdNetworkResponse);
   }
    public LiveData<List<MealById>> getFavMeals(){
        return mealLocalDataSource.getMeals();
    }
    public void insertMealToFav(MealById meal){

        mealLocalDataSource.insertMeal(meal);
    }
}
