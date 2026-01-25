package com.example.food_app_planner.archistartcode.presentation.homepage.presenter;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMeal;
import com.example.food_app_planner.archistartcode.data.datasource.remote.categoryremote.CategoryNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.randommealremote.RandomMealNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.homerandomandcategories.RandomMealRepo;
import com.example.food_app_planner.archistartcode.presentation.homepage.view.HomePageView;

import java.util.List;

public class HomePagePresenterImp implements HomePagePresenter {
    private RandomMealRepo randomMealRepo;
    private HomePageView homePageView;
    public HomePagePresenterImp(Context context,HomePageView homePageView) {
        randomMealRepo=new RandomMealRepo(context);
        this.homePageView=homePageView;
    }
    public void getRandomMeal() {
        randomMealRepo.getRandomMeals(new RandomMealNetworkResponse() {
            @Override
            public void onSuccess(List<RandomMeal> randomMealList) {
                homePageView.onSuccessRandoms(randomMealList);


            }

            @Override
            public void onFailure(String errorMessage) {
                homePageView.onFailureRandoms(errorMessage);

            }
        });


    }

    @Override
    public void getCategories() {
        randomMealRepo.getCatFromRepo(new CategoryNetworkResponse() {

            @Override
            public void onSuccess(List<Category> categoryList) {
                homePageView.onSuccessCategories(categoryList);

            }

            @Override
            public void onFailure(String errorMessage) {
                homePageView.onFailureCategories(errorMessage);

            }
        });

    }
}
