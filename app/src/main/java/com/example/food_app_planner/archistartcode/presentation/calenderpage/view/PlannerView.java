package com.example.food_app_planner.archistartcode.presentation.calenderpage.view;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;

import java.util.List;

public interface PlannerView {
    void onSuccess(LiveData<List<CalenderMeal>> calenderMeals);

}
