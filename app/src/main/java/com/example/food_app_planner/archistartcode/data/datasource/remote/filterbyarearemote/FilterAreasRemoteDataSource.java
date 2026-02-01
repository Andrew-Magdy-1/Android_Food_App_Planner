package com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyarearemote;

import android.util.Log;

import com.example.food_app_planner.archistartcode.data.datasource.models.countries.Country;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbyarea.AreaMeals;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbyarea.AreaMealsResponse;
import com.example.food_app_planner.archistartcode.network.Network;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterAreasRemoteDataSource {
    public FilterAreaService filterAreaService;
    public FilterAreasRemoteDataSource(){
        filterAreaService= Network.getInstance().filterAreaService;
    }
    public Observable<AreaMealsResponse> getAreaMeals(String areaName){
       return filterAreaService.getAreaMeals(areaName);

//        Call<AreaMealsResponse> call=filterAreaService.getAreaMeals(areaName);
//        call.enqueue(new Callback<AreaMealsResponse>() {
//
//            @Override
//            public void onResponse(Call<AreaMealsResponse> call, Response<AreaMealsResponse> response) {
//                if(response.isSuccessful()&&response.body()!=null){
//                    List<AreaMeals> areaMeals=response.body().meals;
//
//                    Log.d("API_TEST", "AreaMeals = " + areaMeals);
//                    filterAreaNetworkResponse.onSuccess(areaMeals);
//
//                }else{
//                    filterAreaNetworkResponse.onFailure("onResponse AreaFilter Api failed");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AreaMealsResponse> call, Throwable t) {
//                if(t instanceof IOException){
//                    filterAreaNetworkResponse.onFailure(t.getMessage());
//                }
//                else{
//                    filterAreaNetworkResponse.onFailure(t.getMessage());
//                }
//
//            }
//        });
    }
}
