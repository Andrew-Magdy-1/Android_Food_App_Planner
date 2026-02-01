package com.example.food_app_planner.archistartcode.database.calendermeal;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CalenderMealDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(CalenderMeal calenderMeal);
    @Query("SELECT * FROM calender_meal WHERE time BETWEEN :startDay AND :endDay")
    Observable<List<CalenderMeal>> getCalenderMeals(long startDay, long endDay);
    @Delete
    Completable delCalenderMeal(CalenderMeal calenderMeal);

    @Query("DELETE FROM calender_meal")
    void clearAll();

    @Query("SELECT * FROM calender_meal WHERE idMeal = :mealId")
    Observable<CalenderMeal> getMealById(String mealId);
}
