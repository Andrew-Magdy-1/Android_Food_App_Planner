package com.example.food_app_planner.archistartcode.data.datasource.remote.ingrediantremote;

import android.util.Log;

import com.example.food_app_planner.archistartcode.data.datasource.models.ingredient.Ingredient;
import com.example.food_app_planner.archistartcode.data.datasource.models.ingredient.IngredientResponse;
import com.example.food_app_planner.archistartcode.network.Network;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientRemoteDataSource {
    private IngredientService ingredientService;

    public IngredientRemoteDataSource() {
        ingredientService = Network.getInstance().ingredientService;
    }

    public void getIngredients(IngredientNetworkResponse ingredientNetworkResponse) {
        Call<IngredientResponse> call = ingredientService.getIngredients();

        call.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Ingredient> ingredientList = response.body().meals;
                    Log.d("API_TEST", "Ingredients = " + ingredientList);
                    ingredientNetworkResponse.onSuccess(ingredientList);
                } else {
                    Log.i("Ingredient", "onResponse Failed");
                    ingredientNetworkResponse.onFailure("Response failed");
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    ingredientNetworkResponse.onFailure(t.getMessage());
                } else {
                    ingredientNetworkResponse.onFailure("ElnetFaselAkked");
                }
            }
        });
    }
}