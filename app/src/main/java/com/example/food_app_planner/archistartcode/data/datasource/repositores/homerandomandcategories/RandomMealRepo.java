package com.example.food_app_planner.archistartcode.data.datasource.repositores.homerandomandcategories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.models.category.CategoryResonse;
import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMealResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.categoryremote.CategoryRemoteDataSource;
import com.example.food_app_planner.archistartcode.data.datasource.remote.randommealremote.RandomMealsRemoteDataSource;


import io.reactivex.rxjava3.core.Observable;

public class RandomMealRepo {
    RandomMealsRemoteDataSource randomMealsRemoteDataSource;
    CategoryRemoteDataSource categoryRemoteDataSource;

    public RandomMealRepo(Context context){
        randomMealsRemoteDataSource=new RandomMealsRemoteDataSource();
        categoryRemoteDataSource=new CategoryRemoteDataSource();
    }
    public Observable<RandomMealResponse> getRandomMeals( ){
        return randomMealsRemoteDataSource.getRandomMeal();

    }

    public Observable<CategoryResonse> getCatFromRepo( ){
       return categoryRemoteDataSource.getCategories();

    }




}
