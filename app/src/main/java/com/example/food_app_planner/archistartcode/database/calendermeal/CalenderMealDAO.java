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

@Dao
public interface CalenderMealDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(CalenderMeal calenderMeal);
    @Query("SELECT * FROM calender_meal WHERE time BETWEEN :startDay AND :endDay")
    LiveData<List<CalenderMeal>> getCalenderMeals(long startDay, long endDay);
    @Delete
    void delCalenderMeal(CalenderMeal calenderMeal);

    @Query("DELETE FROM calender_meal")
    void clearAll();

    @Query("SELECT * FROM calender_meal WHERE idMeal = :mealId")
    CalenderMeal getMealById(String mealId);
}
