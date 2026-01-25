package com.example.food_app_planner.archistartcode.presentation.countrypage.presenter;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.models.countries.Country;
import com.example.food_app_planner.archistartcode.data.datasource.remote.countryremote.CountryNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.countryrepo.CountryRepo;
import com.example.food_app_planner.archistartcode.presentation.countrypage.view.CountryPageView;

import java.util.List;

public class CountryPagePresenterImp implements CountryPagePresenter{
    CountryPageView countryPageView;
    CountryRepo countryRepo;
    public CountryPagePresenterImp(Context context,CountryPageView countryPageView){
        countryRepo=new CountryRepo(context);
        this.countryPageView=countryPageView;


    }
    @Override
    public void getAllAreas() {
        countryRepo.getCountriesFromRepo(new CountryNetworkResponse() {
            @Override
            public void onSunccess(List<Country> countryList) {
                countryPageView.onSuccess(countryList);
            }

            @Override
            public void onFailure(String errorMessage) {
                countryPageView.onFailure(errorMessage);

            }
        });

    }
}
