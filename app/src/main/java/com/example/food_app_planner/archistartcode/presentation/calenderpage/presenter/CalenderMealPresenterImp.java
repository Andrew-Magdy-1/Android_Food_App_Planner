package com.example.food_app_planner.archistartcode.presentation.calenderpage.presenter;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.calendermealrepo.CalenderMealRepo;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalenderMealPresenterImp implements CalenderMealPresenter {
    private CalenderMealRepo calenderMealRepo;

    public CalenderMealPresenterImp(Context context) {
        calenderMealRepo = new CalenderMealRepo(context);
    }

    @Override
    public Observable<List<CalenderMeal>> getCalenderMealPresenetr(long start, long end) {
        return calenderMealRepo.getCalenderMealFromRepo(start, end).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void delCalenderMeal(CalenderMeal calenderMeal) {
        calenderMealRepo.delCalenderMeal(calenderMeal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe();

    }

    @Override
    public void getFromFireStore(String id) {
        calenderMealRepo.removeCalenderMealFromFire(id);
    }


}


