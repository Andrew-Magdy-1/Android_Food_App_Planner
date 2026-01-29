package com.example.food_app_planner.archistartcode.presentation.favouritemeals.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;

import java.util.List;

public interface FavMealPresenter {
    LiveData<List<MealById>>getFavMeals();
    void deleteMealFromFav(MealById mealById);
}
