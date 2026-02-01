package com.example.food_app_planner.archistartcode.data.datasource.repositores.filterbycategoryrepo;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetailsResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbycategory.FilterByCategoryRemoteDataSource;

import io.reactivex.rxjava3.core.Observable;

public class FilterByCategoryRepo {
    public FilterByCategoryRemoteDataSource filterByCategoryRemoteDataSource;
    public FilterByCategoryRepo(Context context){
        filterByCategoryRemoteDataSource=new FilterByCategoryRemoteDataSource();
    }
    public Observable<CategoryDetailsResponse> getCategoryToFilterFromRepo(String catName){
       return filterByCategoryRemoteDataSource.getCategoryToFilterBy(catName);
    }
}
