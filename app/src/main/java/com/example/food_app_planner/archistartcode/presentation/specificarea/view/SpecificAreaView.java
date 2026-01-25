package com.example.food_app_planner.archistartcode.presentation.specificarea.view;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbyarea.AreaMeals;

import java.util.List;

public interface SpecificAreaView {
    void onSuccess(List<AreaMeals> areaMealsList);
    void onFailure(String errorMessage);
}
