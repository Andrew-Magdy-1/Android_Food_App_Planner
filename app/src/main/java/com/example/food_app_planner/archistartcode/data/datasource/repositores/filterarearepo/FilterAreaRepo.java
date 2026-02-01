package com.example.food_app_planner.archistartcode.data.datasource.repositores.filterarearepo;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbyarea.AreaMealsResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyarearemote.FilterAreasRemoteDataSource;

import io.reactivex.rxjava3.core.Observable;

public class FilterAreaRepo {
    public FilterAreasRemoteDataSource filterAreasRemoteDataSource;
    public FilterAreaRepo(Context context){
        filterAreasRemoteDataSource=new FilterAreasRemoteDataSource();

    }
    public Observable<AreaMealsResponse> getAreaMealsFromRepo(String areaname){
        return filterAreasRemoteDataSource.getAreaMeals(areaname);

    }

}
