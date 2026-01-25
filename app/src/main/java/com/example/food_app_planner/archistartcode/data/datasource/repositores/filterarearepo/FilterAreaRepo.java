package com.example.food_app_planner.archistartcode.data.datasource.repositores.filterarearepo;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyarearemote.FilterAreaNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyarearemote.FilterAreaService;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyarearemote.FilterAreasRemoteDataSource;

public class FilterAreaRepo {
    public FilterAreasRemoteDataSource filterAreasRemoteDataSource;
    public FilterAreaRepo(Context context){
        filterAreasRemoteDataSource=new FilterAreasRemoteDataSource();

    }
    public void getAreaMealsFromRepo(String areaname,FilterAreaNetworkResponse filterAreaNetworkResponse){
        filterAreasRemoteDataSource.getAreaMeals(areaname,filterAreaNetworkResponse);

    }

}
