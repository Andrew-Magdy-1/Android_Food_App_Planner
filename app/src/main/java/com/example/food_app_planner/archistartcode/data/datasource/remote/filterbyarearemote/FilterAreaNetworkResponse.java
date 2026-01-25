package com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyarearemote;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbyarea.AreaMeals;

import java.util.List;

public interface FilterAreaNetworkResponse {
    void onSuccess(List<AreaMeals> areaMealsList);
    void onFailure(String errorMessage);
}
