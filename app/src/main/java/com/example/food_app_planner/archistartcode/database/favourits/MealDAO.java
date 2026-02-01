package com.example.food_app_planner.archistartcode.database.favourits;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(MealById mealById);
    @Query("SELECT * FROM meals")
    Observable<List<MealById>> mealList();

    @Delete
    Completable deletMeal(MealById meal);
    @Query("SELECT * FROM meals WHERE idMeal = :mealId")
    Single<MealById> getMealById(String mealId);





}
