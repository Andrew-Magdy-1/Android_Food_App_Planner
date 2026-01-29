package com.example.food_app_planner.archistartcode.database.favourits;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;

import java.util.List;

@Dao
public interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(MealById mealById);
    @Query("SELECT * FROM meals")
    LiveData<List<MealById>>mealList();

    @Delete
    void deletMeal(MealById meal);
    @Query("SELECT * FROM meals WHERE idMeal = :mealId")
    MealById getMealById(String mealId);





}
