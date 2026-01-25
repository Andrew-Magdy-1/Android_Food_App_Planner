package com.example.food_app_planner.archistartcode.data.datasource.remote.randommealremote;

import android.util.Log;

import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMealResponse;
import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMeal;
import com.example.food_app_planner.archistartcode.network.Network;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RandomMealsRemoteDataSource {
    private RandomMealService randomMealService;
    public RandomMealsRemoteDataSource(){
        randomMealService= Network.getInstance().randomMealService;
    }
    public void getRandomMeal(RandomMealNetworkResponse randomMealNetworkResponse){
        Call<RandomMealResponse> call=randomMealService.getRandomMeals();
        call.enqueue(new Callback<RandomMealResponse>() {
            @Override
            public void onResponse(Call<RandomMealResponse> call, Response<RandomMealResponse> response) {
                Log.d("API_TEST", "HTTP CODE = " + response.code());

                if (response.isSuccessful() && response.body() != null) {

                    List<RandomMeal> randomMealList = response.body().meals;
                    Log.d("API_TEST", "Meals = " + randomMealList);

                    randomMealNetworkResponse.onSuccess(randomMealList);

                } else {
                    Log.e("API_TEST", "Response body is null");
                }


            }

            @Override
            public void onFailure(Call<RandomMealResponse> call, Throwable t) {
                if(t instanceof IOException){
                    randomMealNetworkResponse.onFailure(t.getMessage());
                }
                else{
                    randomMealNetworkResponse.onFailure("ElnetFaselAkked");
                }

            }
        });


    }

}
