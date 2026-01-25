package com.example.food_app_planner.archistartcode.presentation.specificarea.presenter;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbyarea.AreaMeals;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyarearemote.FilterAreaNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.filterarearepo.FilterAreaRepo;
import com.example.food_app_planner.archistartcode.presentation.specificarea.view.SpecificAreaView;

import java.util.List;

public class SpecificAreaPresenterImp implements SpecificAreaPresenter{
    private FilterAreaRepo filterAreaRepo;
    private SpecificAreaView specificAreaView;
    private String area;
    public SpecificAreaPresenterImp(Context context,SpecificAreaView specificAreaView,String area) {

        this.area=area;
        filterAreaRepo=new FilterAreaRepo(context);
        this.specificAreaView=specificAreaView;
    }

    @Override
    public void getAllAreaMeal() {
        filterAreaRepo.getAreaMealsFromRepo(area,new FilterAreaNetworkResponse() {
            @Override
            public void onSuccess(List<AreaMeals> areaMealsList) {
                specificAreaView.onSuccess(areaMealsList);

            }

            @Override
            public void onFailure(String errorMessage) {
                specificAreaView.onFailure(errorMessage);
            }
        });

    }
}
