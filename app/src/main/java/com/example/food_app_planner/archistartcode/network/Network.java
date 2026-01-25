package com.example.food_app_planner.archistartcode.network;

import com.example.food_app_planner.archistartcode.data.datasource.remote.categoryremote.CategoryServic;
import com.example.food_app_planner.archistartcode.data.datasource.remote.countryremote.CountryService;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyarearemote.FilterAreaService;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbycategory.FilterByCategoryService;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyidremote.MealByIdService;
import com.example.food_app_planner.archistartcode.data.datasource.remote.randommealremote.RandomMealService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    public RandomMealService randomMealService;
    public CategoryServic categoryServic;
    public CountryService countryService;
    public FilterAreaService filterAreaService;
    public FilterByCategoryService filterByCategoryService;
    public MealByIdService mealByIdService;
    public static Network instanse=null;
    public Network(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        randomMealService=retrofit.create(RandomMealService.class);
        categoryServic=retrofit.create(CategoryServic.class);
        countryService=retrofit.create(CountryService.class);
        filterAreaService=retrofit.create(FilterAreaService.class);
        filterByCategoryService=retrofit.create(FilterByCategoryService.class);
        mealByIdService=retrofit.create(MealByIdService.class);
    }
    public static Network getInstance(){
        if(instanse==null)instanse=new Network();
        return instanse;
    }



}
