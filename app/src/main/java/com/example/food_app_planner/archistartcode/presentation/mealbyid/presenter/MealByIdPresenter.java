package com.example.food_app_planner.archistartcode.presentation.mealbyid.presenter;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;

public interface MealByIdPresenter {
    void getMealById();
    void insertProductToFav(MealById meal);
    void insertToCalender(CalenderMeal calenderMeal);
    //boolean ifUserIsAnAnonymous( );
}
