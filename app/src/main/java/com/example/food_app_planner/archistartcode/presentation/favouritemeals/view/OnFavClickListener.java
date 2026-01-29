package com.example.food_app_planner.archistartcode.presentation.favouritemeals.view;

import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;

public interface OnFavClickListener {
    void onClickDel(MealById mealById);
    void delFromFire(String id);
}
