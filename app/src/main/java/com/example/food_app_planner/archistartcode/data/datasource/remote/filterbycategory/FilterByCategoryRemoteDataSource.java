package com.example.food_app_planner.archistartcode.data.datasource.remote.filterbycategory;

import android.util.Log;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetailsResponse;
import com.example.food_app_planner.archistartcode.network.Network;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterByCategoryRemoteDataSource {
    public FilterByCategoryService filterByCategoryService;
    public FilterByCategoryRemoteDataSource(){
        filterByCategoryService= Network.getInstance().filterByCategoryService;
    }
   public void getCategoryToFilterBy(String categoryName,FilterByCategoryNetworkResponse filterByCategoryNetworkResponse){
        Call<CategoryDetailsResponse> call=filterByCategoryService.getCategoryToFilter(categoryName);
        call.enqueue(new Callback<CategoryDetailsResponse>() {
            @Override
            public void onResponse(Call<CategoryDetailsResponse> call, Response<CategoryDetailsResponse> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    List<CategoryDetails> categoryDetailsList=response.body().meals;
                    Log.d("API_TEST", "CategoryDetails = " + categoryDetailsList);
                    filterByCategoryNetworkResponse.onSuccess(categoryDetailsList);
                }else {
                    filterByCategoryNetworkResponse.onFailure("onResponse Filter by cat fialed");
                }
            }

            @Override
            public void onFailure(Call<CategoryDetailsResponse> call, Throwable t) {
                if(t instanceof IOException) {
                    filterByCategoryNetworkResponse.onFailure(t.getMessage());
                }else {
                    filterByCategoryNetworkResponse.onFailure(t.getMessage());
                }

            }
        });
    }
}
