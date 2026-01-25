package com.example.food_app_planner.archistartcode.data.datasource.remote.filterbycategory;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;

import java.util.List;

public interface FilterByCategoryNetworkResponse {
    void onSuccess(List<CategoryDetails> categoryDetailsList);
    void onFailure(String errorMessage);
}
