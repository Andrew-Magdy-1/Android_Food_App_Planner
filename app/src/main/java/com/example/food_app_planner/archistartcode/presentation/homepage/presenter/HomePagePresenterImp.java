package com.example.food_app_planner.archistartcode.presentation.homepage.presenter;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.homerandomandcategories.RandomMealRepo;
import com.example.food_app_planner.archistartcode.presentation.homepage.view.HomePageView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePagePresenterImp implements HomePagePresenter {
    private RandomMealRepo randomMealRepo;
    private HomePageView homePageView;
    public HomePagePresenterImp(Context context,HomePageView homePageView) {
        randomMealRepo=new RandomMealRepo(context);
        this.homePageView=homePageView;
    }
    public void getRandomMeal() {
        randomMealRepo.getRandomMeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item->{
                    homePageView.onSuccessRandoms(item.meals);
                });
    }

    @Override
    public void getCategories() {
        randomMealRepo.getCatFromRepo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item->{
                    List<Category> categoryList=item.categories;
                    homePageView.onSuccessCategories(categoryList);

                },
                error->{
                    homePageView.onFailureCategories(error.getMessage());
                }
                );
    }




}
