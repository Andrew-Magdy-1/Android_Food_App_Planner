package com.example.food_app_planner.archistartcode.database.calendermeal;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;

import java.util.List;

@Dao
public interface CalenderMealDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(CalenderMeal calenderMeal);
    @Query("SELECT * FROM calender_meal WHERE time BETWEEN :startDay AND :endDay")
    LiveData<List<CalenderMeal>> getCalenderMeals(long startDay, long endDay);
    @Query("SELECT * FROM calender_meal WHERE userId = :userId AND timestamp BETWEEN :startDay AND :endDay ORDER BY timestamp DESC")
    LiveData<List<CalenderMeal>> getCalenderMealsByUser(String userId, long startDay, long endDay);
    @Delete
    void delCalenderMeal(CalenderMeal calenderMeal);
}
