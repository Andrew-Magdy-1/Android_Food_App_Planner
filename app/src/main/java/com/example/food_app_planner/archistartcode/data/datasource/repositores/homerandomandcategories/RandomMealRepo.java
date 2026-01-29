package com.example.food_app_planner.archistartcode.data.datasource.repositores.homerandomandcategories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.local.mealtofavourite.MealLocalDataSource;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.remote.categoryremote.CategoryNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.categoryremote.CategoryRemoteDataSource;
import com.example.food_app_planner.archistartcode.data.datasource.remote.randommealremote.RandomMealNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.randommealremote.RandomMealsRemoteDataSource;

import java.util.List;

public class RandomMealRepo {
    RandomMealsRemoteDataSource randomMealsRemoteDataSource;
    CategoryRemoteDataSource categoryRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;

    public RandomMealRepo(Context context){
        randomMealsRemoteDataSource=new RandomMealsRemoteDataSource();
        categoryRemoteDataSource=new CategoryRemoteDataSource();
    }
    public void getRandomMeals(RandomMealNetworkResponse randomMealNetworkResponse){
        randomMealsRemoteDataSource.getRandomMeal(randomMealNetworkResponse);

    }

    public void getCatFromRepo(CategoryNetworkResponse categoryNetworkResponse){
        categoryRemoteDataSource.getCategories(categoryNetworkResponse);

    }
    public LiveData<List<MealById>> getFavMeals(){
        return mealLocalDataSource.getMeals();
    }




}
