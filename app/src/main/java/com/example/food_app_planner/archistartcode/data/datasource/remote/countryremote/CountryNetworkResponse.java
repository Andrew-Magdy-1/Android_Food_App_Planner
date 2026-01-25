package com.example.food_app_planner.archistartcode.data.datasource.remote.countryremote;

import com.example.food_app_planner.archistartcode.data.datasource.models.countries.Country;

import java.util.List;

public interface CountryNetworkResponse {
    void onSunccess(List<Country> countryList);
    void onFailure(String errorMessage);
}
