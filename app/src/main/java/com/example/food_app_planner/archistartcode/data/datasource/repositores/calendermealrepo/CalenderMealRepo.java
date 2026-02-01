package com.example.food_app_planner.archistartcode.data.datasource.repositores.calendermealrepo;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.local.calendermeal.CalenderMealDataSource;
import com.example.food_app_planner.archistartcode.data.datasource.local.mealtofavourite.MealLocalDataSource;
import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class CalenderMealRepo {
    private CalenderMealDataSource calenderMealDataSource;

    public CalenderMealRepo(Context context){
        calenderMealDataSource=new CalenderMealDataSource(context);

    }
    public Observable<List<CalenderMeal>> getCalenderMealFromRepo(long start, long end){
        return calenderMealDataSource.getCalenderMeals( start, end);

    }
    public Completable delCalenderMeal(CalenderMeal calenderMeal) {
       return calenderMealDataSource.delCalenderMeal(calenderMeal);
    }
    public Completable insertCalenderMeal(CalenderMeal calenderMeal){
        return calenderMealDataSource.inserCalenderMeal(calenderMeal);
    }
    public void getCalenderMealFomeremote(){
        calenderMealDataSource.getCalenderMealFirestore();
    }

    public void removeCalenderMealFromFire(String id){
        calenderMealDataSource.delFromFire(id);
    }

}