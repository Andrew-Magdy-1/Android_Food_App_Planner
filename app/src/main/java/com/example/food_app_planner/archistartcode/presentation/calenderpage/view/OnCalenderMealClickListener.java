package com.example.food_app_planner.archistartcode.presentation.calenderpage.view;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;

public interface OnCalenderMealClickListener {
    void onClickDel(CalenderMeal calenderMeal);
    void delFromFire(String id);
}
