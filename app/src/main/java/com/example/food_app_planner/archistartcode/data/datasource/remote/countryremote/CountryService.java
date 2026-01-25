package com.example.food_app_planner.archistartcode.data.datasource.remote.countryremote;

import com.example.food_app_planner.archistartcode.data.datasource.models.countries.CountryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CountryService {
    @GET("list.php")
    Call<CountryResponse> getCountries(@Query("a") String area);
}
