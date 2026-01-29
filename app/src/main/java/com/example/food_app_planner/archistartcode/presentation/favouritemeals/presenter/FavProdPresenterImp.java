package com.example.food_app_planner.archistartcode.presentation.favouritemeals.presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.local.mealtofavourite.MealLocalDataSource;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.filtermealbyidrepo.MealByIdRepo;
import com.example.food_app_planner.archistartcode.presentation.favouritemeals.view.FavView;

import java.util.List;

public class FavProdPresenterImp implements FavMealPresenter {
    MealLocalDataSource mealLocalDataSource;
    FavView favView;
    MealByIdRepo meal;
    public FavProdPresenterImp(Context context,FavView favView){
        mealLocalDataSource=new MealLocalDataSource(context);
        this.favView=favView;
        meal=new MealByIdRepo(context);
    }

    @Override
    public LiveData<List<MealById>> getFavMeals() {
        return meal.getFavMeals();
    }

    @Override
    public void deleteMealFromFav(MealById mealById) {
        mealLocalDataSource.deleteMeal(mealById);

    }

    @Override
    public void getFavouritsFromFav() {
        meal.getFaovuristFire();
    }

    @Override
    public void deleteFromFire(String id) {
        meal.delFromFire(id);

    }


}
