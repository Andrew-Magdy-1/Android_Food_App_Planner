package com.example.food_app_planner.archistartcode.presentation.calenderpage.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface CalenderMealPresenter {
    Observable<List<CalenderMeal>> getCalenderMealPresenetr(long start, long end);
    void delCalenderMeal(CalenderMeal calenderMeal);
    void getFromFireStore(String id);
}
