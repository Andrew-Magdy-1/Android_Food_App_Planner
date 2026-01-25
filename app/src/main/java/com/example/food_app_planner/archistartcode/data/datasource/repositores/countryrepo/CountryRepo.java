package com.example.food_app_planner.archistartcode.data.datasource.repositores.countryrepo;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.models.countries.CountryResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.countryremote.CountryNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.countryremote.CountryRemoteDataSource;

public class CountryRepo {
    public CountryRemoteDataSource countryRemoteDataSource;
    public CountryRepo(Context context){
        countryRemoteDataSource=new CountryRemoteDataSource();
    }
    public void getCountriesFromRepo(CountryNetworkResponse countryNetworkResponse){
        countryRemoteDataSource.getCountries(countryNetworkResponse);
    }
}
