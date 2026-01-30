package com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyingrediant;

import android.util.Log;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetailsResponse;
import com.example.food_app_planner.archistartcode.network.Network;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterByIngredientRemoteDataSource {
    private FilterByIngredientService filterByIngredientService;

    public FilterByIngredientRemoteDataSource() {
        filterByIngredientService = Network.getInstance().filterByIngredientService;
    }

    public void getIngredientMeals(String ingredientName, FilterByIngredientNetworkResponse filterByIngredientNetworkResponse) {
        Call<CategoryDetailsResponse> call = filterByIngredientService.filterByIngredient(ingredientName);

        call.enqueue(new Callback<CategoryDetailsResponse>() {
            @Override
            public void onResponse(Call<CategoryDetailsResponse> call, Response<CategoryDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CategoryDetails> mealList = response.body().meals;
                    Log.d("API_TEST", "Ingredient Meals = " + mealList);
                    filterByIngredientNetworkResponse.onSunccess(mealList);
                } else {
                    Log.i("FilterByIngredient", "onResponse Failed");
                    filterByIngredientNetworkResponse.onFailure("Response failed");
                }
            }

            @Override
            public void onFailure(Call<CategoryDetailsResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    filterByIngredientNetworkResponse.onFailure(t.getMessage());
                } else {
                    filterByIngredientNetworkResponse.onFailure("ElnetFaselAkked");
                }
            }
        });
    }
}