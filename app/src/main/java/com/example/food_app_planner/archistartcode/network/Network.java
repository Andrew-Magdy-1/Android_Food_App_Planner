package com.example.food_app_planner.archistartcode.network;

import com.example.food_app_planner.archistartcode.datasource.remote.categoryremote.CategoryServic;
import com.example.food_app_planner.archistartcode.datasource.remote.randommealremote.RandomMealService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    public RandomMealService randomMealService;
    public CategoryServic categoryServic;
    public static Network instanse=null;
    public Network(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        randomMealService=retrofit.create(RandomMealService.class);
        categoryServic=retrofit.create(CategoryServic.class);
    }
    public static Network getInstance(){
        if(instanse==null)instanse=new Network();
        return instanse;
    }



}
