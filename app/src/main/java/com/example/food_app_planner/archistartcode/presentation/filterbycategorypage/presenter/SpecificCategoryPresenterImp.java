package com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.presenter;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.repositores.filterbycategoryrepo.FilterByCategoryRepo;
import com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.view.SpecificCategoryView;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
         filterByCategoryRepo.getCategoryToFilterFromRepo(catName).subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread()).subscribe(item->{
                     specificCategoryView.onSuccess(item.meals);
                 },error->{
                     specificCategoryView.onFailure(error.getMessage());

                 });
//         filterByCategoryRepo.getCategoryToFilterFromRepo(catName, new FilterByCategoryNetworkResponse() {
//             @Override
//             public void onSuccess(List<CategoryDetails> categoryDetailsList) {
//                 specificCategoryView.onSuccess(categoryDetailsList);
//
//             }
//
//             @Override
//             public void onFailure(String errorMessage) {
//                 specificCategoryView.onFailure(errorMessage);
//
//             }
//         });


    }
}
