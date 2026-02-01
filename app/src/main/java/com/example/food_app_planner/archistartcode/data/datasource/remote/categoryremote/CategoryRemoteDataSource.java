package com.example.food_app_planner.archistartcode.data.datasource.remote.categoryremote;


import android.util.Log;

import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
import com.example.food_app_planner.archistartcode.data.datasource.models.category.CategoryResonse;
import com.example.food_app_planner.archistartcode.network.Network;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRemoteDataSource {
    private CategoryServic categoryServic;
    public CategoryRemoteDataSource(){
        categoryServic= Network.getInstance().categoryServic;

    }//CategoryResonse
    public Observable<CategoryResonse> getCategories(){
        return categoryServic.getCategories();


    }

}
