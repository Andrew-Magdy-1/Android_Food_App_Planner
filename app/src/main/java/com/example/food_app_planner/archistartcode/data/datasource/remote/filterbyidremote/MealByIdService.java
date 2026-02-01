package com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyidremote;

import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealByIdResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealByIdService {
    @GET("lookup.php")
    Observable<MealByIdResponse> getMealById(@Query("i") String id);
}
