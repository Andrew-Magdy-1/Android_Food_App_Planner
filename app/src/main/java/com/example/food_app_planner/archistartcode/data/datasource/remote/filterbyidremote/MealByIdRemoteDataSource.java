package com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyidremote;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealByIdResponse;
import com.example.food_app_planner.archistartcode.network.Network;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealByIdRemoteDataSource {
    public MealByIdService mealByIdService;
    public MealByIdRemoteDataSource() {
        mealByIdService= Network.getInstance().mealByIdService;
    }

    public Observable<MealByIdResponse> getMealById(String id){
        return mealByIdService.getMealById(id);
    }

}
