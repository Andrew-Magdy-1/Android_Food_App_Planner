package com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.presenter;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbycategory.FilterByCategoryNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.filterbycategoryrepo.FilterByCategoryRepo;
import com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.view.SpecificCategoryView;

import java.util.List;

public class SpecificCategoryPresenterImp implements SpecificCategoryPresenter{
     FilterByCategoryRepo filterByCategoryRepo;
     SpecificCategoryView specificCategoryView;
     public String catName;

     public SpecificCategoryPresenterImp(Context context,SpecificCategoryView specificCategoryView,String catName) {
         filterByCategoryRepo=new FilterByCategoryRepo(context);
         this.catName=catName;
         this.specificCategoryView=specificCategoryView;
     }

    @Override
    public void getAllCategoryMeals() {
         filterByCategoryRepo.getCategoryToFilterFromRepo(catName, new FilterByCategoryNetworkResponse() {
             @Override
             public void onSuccess(List<CategoryDetails> categoryDetailsList) {
                 specificCategoryView.onSuccess(categoryDetailsList);

             }

             @Override
             public void onFailure(String errorMessage) {
                 specificCategoryView.onFailure(errorMessage);

             }
         });


    }
}
