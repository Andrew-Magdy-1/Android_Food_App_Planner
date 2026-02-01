package com.example.food_app_planner.archistartcode.presentation.specificarea.presenter;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.repositores.filterarearepo.FilterAreaRepo;
import com.example.food_app_planner.archistartcode.presentation.specificarea.view.SpecificAreaView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
        filterAreaRepo.getAreaMealsFromRepo(area).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(item->{
                    specificAreaView.onSuccess(item.meals);
                },error->{
                    specificAreaView.onFailure(error.getMessage());
                });

    }
}
