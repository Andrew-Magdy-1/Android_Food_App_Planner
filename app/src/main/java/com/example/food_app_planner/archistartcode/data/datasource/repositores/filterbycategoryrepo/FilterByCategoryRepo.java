package com.example.food_app_planner.archistartcode.data.datasource.repositores.filterbycategoryrepo;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbycategory.FilterByCategoryNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbycategory.FilterByCategoryRemoteDataSource;

public class FilterByCategoryRepo {
    public FilterByCategoryRemoteDataSource filterByCategoryRemoteDataSource;
    public FilterByCategoryRepo(Context context){
        filterByCategoryRemoteDataSource=new FilterByCategoryRemoteDataSource();
    }
    public void getCategoryToFilterFromRepo(String catName,FilterByCategoryNetworkResponse filterByCategoryNetworkResponse){
        filterByCategoryRemoteDataSource.getCategoryToFilterBy(catName,filterByCategoryNetworkResponse);
    }
}
