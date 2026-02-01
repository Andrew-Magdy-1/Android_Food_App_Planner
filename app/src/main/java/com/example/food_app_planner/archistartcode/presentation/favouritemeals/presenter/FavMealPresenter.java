package com.example.food_app_planner.archistartcode.presentation.favouritemeals.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface FavMealPresenter {
    Observable<List<MealById>> getFavMeals();
    void deleteMealFromFav(MealById mealById);
    void getFavouritsFromFav();
    void deleteFromFire(String id);
}
