package com.example.food_app_planner.archistartcode.data.datasource.remote.categoryremote;


import android.util.Log;

import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
import com.example.food_app_planner.archistartcode.data.datasource.models.category.CategoryResonse;
import com.example.food_app_planner.archistartcode.network.Network;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRemoteDataSource {
    private CategoryServic categoryServic;
    public CategoryRemoteDataSource(){
        categoryServic= Network.getInstance().categoryServic;

    }
    public void getCategories(CategoryNetworkResponse categoryNetworkResponse){
        Call<CategoryResonse>  call=categoryServic.getCategories();

        call.enqueue(new Callback<CategoryResonse>() {
            @Override
            public void onResponse(Call<CategoryResonse> call, Response<CategoryResonse> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    List<Category> categoryList=response.body().categories;
                    Log.d("API_TEST", "Cat = " + categoryList);
                    categoryNetworkResponse.onSuccess(categoryList);
                }else{
                    Log.i("Category","onResponse Fsiled");
                }
            }

            @Override
            public void onFailure(Call<CategoryResonse> call, Throwable t) {
                if(t instanceof IOException){
                    categoryNetworkResponse.onFailure(t.getMessage());
                }
                else{
                    categoryNetworkResponse.onFailure("ElnetFaselAkked");
                }

            }
        });

    }}
