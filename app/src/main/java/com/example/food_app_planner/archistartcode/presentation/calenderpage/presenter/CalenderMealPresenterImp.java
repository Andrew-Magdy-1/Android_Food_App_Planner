package com.example.food_app_planner.archistartcode.presentation.calenderpage.presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.calendermealrepo.CalenderMealRepo;
import com.example.food_app_planner.archistartcode.presentation.calenderpage.view.PlannerView;

import java.util.List;

public class CalenderMealPresenterImp implements CalenderMealPresenter{
    private CalenderMealRepo calenderMealRepo;
    public CalenderMealPresenterImp(Context  context){
        calenderMealRepo=new CalenderMealRepo(context);
    }

    @Override
    public LiveData<List<CalenderMeal>> getCalenderMealPresenetr(long start,long end) {
        return calenderMealRepo.getCalenderMealFromRepo(start,end);
    }

    @Override
    public void delCalenderMeal(CalenderMeal calenderMeal) {
        calenderMealRepo.delCalenderMeal(calenderMeal);

    }


}
