package com.example.food_app_planner.archistartcode.datasource.remote.categoryremote;

import com.example.food_app_planner.archistartcode.datasource.models.category.Category;

import java.util.List;

public interface CategoryNetworkResponse {
    void onSuccess(List<Category> categoryList) ;
    void onFailure(String errorMessage);
}
