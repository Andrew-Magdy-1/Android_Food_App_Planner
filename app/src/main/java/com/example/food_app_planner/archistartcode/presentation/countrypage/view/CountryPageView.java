package com.example.food_app_planner.archistartcode.presentation.countrypage.view;

import com.example.food_app_planner.archistartcode.data.datasource.models.countries.Country;

import java.util.List;

public interface CountryPageView {
    void onSuccess(List<Country> countryList);
    void onFailure(String errorMessage);
}
