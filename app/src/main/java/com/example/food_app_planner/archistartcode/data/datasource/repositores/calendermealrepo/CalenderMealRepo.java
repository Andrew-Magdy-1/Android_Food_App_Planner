package com.example.food_app_planner.archistartcode.data.datasource.repositores.calendermealrepo;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.local.calendermeal.CalenderMealDataSource;
import com.example.food_app_planner.archistartcode.data.datasource.local.mealtofavourite.MealLocalDataSource;
import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;

import java.util.List;

public class CalenderMealRepo {
    private CalenderMealDataSource calenderMealDataSource;

    public CalenderMealRepo(Context context){
        calenderMealDataSource=new CalenderMealDataSource(context);

    }
    public LiveData<List<CalenderMeal>> getCalenderMealFromRepo(long start,long end){
        return calenderMealDataSource.getCalenderMeals( start, end);

    }
    public void delCalenderMeal(CalenderMeal calenderMeal) {
        calenderMealDataSource.delCalenderMeal(calenderMeal);
    }
    public void insertCalenderMeal(CalenderMeal calenderMeal){
        calenderMealDataSource.inserCalenderMeal(calenderMeal);
    }
    public void getCalenderMealFomeremote(){
        calenderMealDataSource.getCalenderMealFirestore();
    }

    public void removeCalenderMealFromFire(String id){
        calenderMealDataSource.delFromFire(id);
    }

}