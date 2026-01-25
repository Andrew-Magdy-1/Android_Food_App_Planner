package com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyidremote;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealByIdResponse;
import com.example.food_app_planner.archistartcode.network.Network;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealByIdRemoteDataSource {
    public MealByIdService mealByIdService;
    public MealByIdRemoteDataSource() {
        mealByIdService= Network.getInstance().mealByIdService;
    }

    public void getMealById(String id,MealByIdNetworkResponse mealByIdNetworkResponse){
        Call<MealByIdResponse> call=mealByIdService.getMealById(id);
        call.enqueue(new Callback<MealByIdResponse>() {
            @Override
            public void onResponse(Call<MealByIdResponse> call, Response<MealByIdResponse> response) {
                if(response.isSuccessful()&&response.body()!=null) {
                    List<MealById> mealByIdList = response.body().meals;
                    mealByIdNetworkResponse.onSuccess(mealByIdList);
                }else{
                    mealByIdNetworkResponse.onFailure("onResponse on mealby id failed");
                }
            }

            @Override
            public void onFailure(Call<MealByIdResponse> call, Throwable t) {
                if(t instanceof IOException){
                    mealByIdNetworkResponse.onFailure(t.getMessage());
                }else{
                    mealByIdNetworkResponse.onFailure(t.getMessage());
                }

            }
        });

    }

}
