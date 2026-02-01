package com.example.food_app_planner.archistartcode.data.datasource.repositores.filtermealbyidrepo;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.local.mealtofavourite.MealLocalDataSource;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealByIdResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyidremote.MealByIdRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class MealByIdRepo {
   private MealByIdRemoteDataSource mealByIdRemoteDataSource;
   private MealLocalDataSource mealLocalDataSource;
   public MealByIdRepo(Context context){
       mealByIdRemoteDataSource=new MealByIdRemoteDataSource();
       mealLocalDataSource=new MealLocalDataSource(context);
   }
   public Observable<MealByIdResponse> getMealByIdFromRepo(String id){
      return mealByIdRemoteDataSource.getMealById(id);
   }
    public Observable<List<MealById>> getFavMeals(){
        return mealLocalDataSource.getMeals();
    }
    public Completable insertMealToFav(MealById meal){

       return mealLocalDataSource.insertMeal(meal);
    }
    public void getFaovuristFire(){
        mealLocalDataSource.getFavoritesFirestore();
    }
    public Completable deleteMeal(MealById mealById) {
        return mealLocalDataSource.deleteMeal(mealById);

    }
    public void delFromFire(String id) {
        mealLocalDataSource.removeFromFire(id);
    }
}
