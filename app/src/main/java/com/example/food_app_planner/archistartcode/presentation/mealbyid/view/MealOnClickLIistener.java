package com.example.food_app_planner.archistartcode.presentation.mealbyid.view;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;

public interface MealOnClickLIistener {
    void addMealToFav(MealById meal);
    void addMealToPlan(CalenderMeal calenderMeal);


}
