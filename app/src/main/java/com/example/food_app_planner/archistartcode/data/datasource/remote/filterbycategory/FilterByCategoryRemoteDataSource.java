package com.example.food_app_planner.archistartcode.data.datasource.remote.filterbycategory;

import android.util.Log;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetailsResponse;
import com.example.food_app_planner.archistartcode.network.Network;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterByCategoryRemoteDataSource {
    public FilterByCategoryService filterByCategoryService;
    public FilterByCategoryRemoteDataSource(){
        filterByCategoryService= Network.getInstance().filterByCategoryService;
    }
   public Observable<CategoryDetailsResponse> getCategoryToFilterBy(String categoryName){
       return filterByCategoryService.getCategoryToFilter(categoryName);
    }
}
