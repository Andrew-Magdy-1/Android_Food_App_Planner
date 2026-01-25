package com.example.food_app_planner.archistartcode;

import android.os.Bundle;

import android.util.Log;
import android.widget.Button;


import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
import com.example.food_app_planner.archistartcode.data.datasource.models.countries.Country;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbyarea.AreaMeals;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;
import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMeal;
import com.example.food_app_planner.archistartcode.data.datasource.remote.categoryremote.CategoryNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.countryremote.CountryNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyarearemote.FilterAreaNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbycategory.FilterByCategoryNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.randommealremote.RandomMealNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.countryrepo.CountryRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.filterarearepo.FilterAreaRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.filterbycategoryrepo.FilterByCategoryRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.homerandomandcategories.RandomMealRepo;

import java.util.List;

public class AuthActivity extends AppCompatActivity implements onSignUpClickListener{
    Button signup;
    FragmentManager manager;
    FragmentTransaction transaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
        //SignIn_Fragment signInFragment=new SignIn_Fragment();
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        if(savedInstanceState==null){

            SignIn_Fragment signInFragment = new SignIn_Fragment();

        }
        RandomMealRepo repo = new RandomMealRepo(this);

        repo.getRandomMeals(new RandomMealNetworkResponse() {
            @Override
            public void onSuccess(List<RandomMeal> randomMealList) {
                //Log.d("API_TEST", "SUCCESS size = " + randomMealList);
                //Log.d("API_TEST", "First meal = " + randomMealList.get(0).toString());
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("API_TEST", "FAIL = " + errorMessage);
            }
        });

        repo = new RandomMealRepo(this);
        repo.getCatFromRepo(new CategoryNetworkResponse() {
            @Override
            public void onSuccess(List<Category> categoryList) {
               //  Log.d("c", "SUCCESS size = " + categoryList);
               // Log.d("API_TEST", "First meal = " + categoryList.get(0).toString());

            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });

        CountryRepo countryRepo=new CountryRepo(this);
        countryRepo.getCountriesFromRepo(new CountryNetworkResponse() {
            @Override
            public void onSunccess(List<Country> countryList) {
                //Log.d("c", "SUCCESS size = " + countryList);
               // Log.d("API_TEST", "First meal = " + countryList.get(0).toString());

            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
//        FilterAreaRepo filterAreaRepo=new FilterAreaRepo(this);
//        filterAreaRepo.getAreaMealsFromRepo(new FilterAreaNetworkResponse() {
//            @Override
//            public void onSuccess(List<AreaMeals> areaMealsList) {
//
//            }
//
//            @Override
//            public void onFailure(String errorMessage) {
//
//            }
//        });

//        FilterByCategoryRepo filterByCategoryRepo=new FilterByCategoryRepo(this);
//        filterByCategoryRepo.getCategoryToFilterFromRepo(new FilterByCategoryNetworkResponse() {
//            @Override
//            public void onSuccess(List<CategoryDetails> categoryDetailsList) {
//
//            }
//
//            @Override
//            public void onFailure(String errorMessage) {
//
//            }
//        });






    }


    @Override
    public void onSignUpClickListener(Fragment f) {
        transaction.add(R.id.fragmentContainerView3,f);
        transaction.commit();

    }
}