package com.example.food_app_planner.archistartcode.presentation.mealbyid.presenter;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyidremote.MealByIdNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.filtermealbyidrepo.MealByIdRepo;
import com.example.food_app_planner.archistartcode.presentation.mealbyid.view.MealByIdView;

import java.util.List;

public class MealByIdPresenterImp implements MealByIdPresenter{
    private MealByIdView mealByIdView;
    private MealByIdRepo mealByIdRepo;
    private String id;
    public MealByIdPresenterImp(Context context,MealByIdView mealByIdView,String id){
        mealByIdRepo=new MealByIdRepo(context);
        this.mealByIdView=mealByIdView;
        this.id=id;
    }
    @Override
    public void getMealById() {
        mealByIdRepo.getMealByIdFromRepo(id, new MealByIdNetworkResponse() {
            @Override
            public void onSuccess(List<MealById> mealByIdList) {
                mealByIdView.onSuccess(mealByIdList.get(0));
            }

            @Override
            public void onFailure(String errorMesage) {

            }
        });


    }
}
