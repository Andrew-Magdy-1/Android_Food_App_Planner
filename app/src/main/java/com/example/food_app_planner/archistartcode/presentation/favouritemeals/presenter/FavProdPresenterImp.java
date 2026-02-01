package com.example.food_app_planner.archistartcode.presentation.favouritemeals.presenter;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.filtermealbyidrepo.MealByIdRepo;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavProdPresenterImp implements FavMealPresenter {
   // FavView favView;
    MealByIdRepo meal;
    public FavProdPresenterImp(Context context){
       // this.favView=favView;
        meal=new MealByIdRepo(context);
    }

    @Override
    public Observable<List<MealById>> getFavMeals() {

        return meal.getFavMeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void deleteMealFromFav(MealById mealById) {
        meal.deleteMeal(mealById).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

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
