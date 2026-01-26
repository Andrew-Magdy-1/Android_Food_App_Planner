package com.example.food_app_planner.archistartcode.presentation.mealbyid.view;

import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;

import java.util.List;

public interface MealByIdView {
    void onSuccess(MealById mealByIdList);
    void onFailure(String errorMessage);
}
