package com.example.food_app_planner.archistartcode.data.datasource.local.mealtofavourite;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.database.favourits.AddMealToDb;
import com.example.food_app_planner.archistartcode.database.favourits.MealDAO;

import java.util.List;

public class MealLocalDataSource {
    private MealDAO mealDAO;

    public MealLocalDataSource(Context context){
        AddMealToDb addMealToDb=AddMealToDb.getInstance(context);
        mealDAO=addMealToDb.mealDAO();

    }
    public void insertMeal(MealById mealById){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.insertMeal(mealById);
            }
        }).start();
    }
    public void deleteMeal(MealById mealById){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.deletMeal(mealById);
            }
        }).start();
    }

    public LiveData<List<MealById>> getMeals() {
        return mealDAO.mealList();
    }


}
