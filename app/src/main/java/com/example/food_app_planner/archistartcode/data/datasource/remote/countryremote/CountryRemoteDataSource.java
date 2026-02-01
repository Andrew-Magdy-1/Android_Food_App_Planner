package com.example.food_app_planner.archistartcode.data.datasource.remote.countryremote;

import android.content.Context;
import android.util.Log;

import com.example.food_app_planner.archistartcode.data.datasource.models.countries.Country;
import com.example.food_app_planner.archistartcode.data.datasource.models.countries.CountryResponse;
import com.example.food_app_planner.archistartcode.network.Network;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryRemoteDataSource {
    private CountryService countryService;
    public CountryRemoteDataSource(){
        countryService= Network.getInstance().countryService;
    }
    public Observable<CountryResponse> getCountries(String name){
          return countryService.getCountries("a");
//        Call<CountryResponse> call= countryService.getCountries("a");
//        call.enqueue(new Callback<CountryResponse>() {
//            @Override
//            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
//                if(response.isSuccessful()&&response.body()!=null){
//                    List<Country> countryList=response.body().meals;
//
//                    Log.d("API_TEST", "Country = " + countryList);
//                    countryNetworkResponse.onSunccess(countryList);
//
//                }else{
//                    countryNetworkResponse.onFailure("onResponse Country Api failed");
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<CountryResponse> call, Throwable t) {
//                if(t instanceof IOException){
//                    countryNetworkResponse.onFailure(t.getMessage());
//                }
//                else{
//                    countryNetworkResponse.onFailure(t.getMessage());
//                }
//
//
//            }
//        });
    }
}
