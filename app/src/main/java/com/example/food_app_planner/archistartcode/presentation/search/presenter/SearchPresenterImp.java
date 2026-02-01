package com.example.food_app_planner.archistartcode.presentation.search.presenter;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.models.ingredient.Ingredient;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.countryrepo.CountryRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.filterarearepo.FilterAreaRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.filterbycategoryrepo.FilterByCategoryRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.filterbyingredientrepo.FilterByIngredientRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.homerandomandcategories.RandomMealRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.ingredientrepo.IngredientRepo;
import com.example.food_app_planner.archistartcode.presentation.search.view.SearchView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImp implements SearchPresenter{
    private SearchView searchView;
    private RandomMealRepo countryRepo;
    private IngredientRepo ingredientRepo;
    private FilterByCategoryRepo filterByCategoryRepo;
    private FilterAreaRepo filterAreaRepo;
    private FilterByIngredientRepo filterByIngredientRepo;
    private CountryRepo country;

    public SearchPresenterImp(Context context, SearchView searchView) {
        this.searchView = searchView;
       // categoryRepo = new CategoryRepo(context);
        countryRepo = new RandomMealRepo(context);
        ingredientRepo = new IngredientRepo(context);
        filterByCategoryRepo = new FilterByCategoryRepo(context);
        filterAreaRepo = new FilterAreaRepo(context);
        filterByIngredientRepo = new FilterByIngredientRepo(context);
        country=new CountryRepo(context);
    }

    @Override
    public void getAllCategories() {
        countryRepo.getCatFromRepo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(item->{
                    searchView.onCategoriesSuccess(item.categories);
                });
    }

    @Override
    public void getAllCountries() {
        country.getCountriesFromRepo().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(item->{
                                    searchView.onCountriesSuccess(item.meals);
                                });
//        country.getCountriesFromRepo(new CountryNetworkResponse() {
//            @Override
//            public void onSunccess(List<Country> countryList) {
//                searchView.onCountriesSuccess(countryList);
//            }
//
//            @Override
//            public void onFailure(String errorMessage) {
//                searchView.onFailure(errorMessage);
//            }
//        });
    }

    @Override
    public void getAllIngredients() {
        ingredientRepo.getIngredientsFromRepo().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(item->{searchView.onIngredientsSuccess(item.meals);},
                                        error->{searchView.onFailure(error.getMessage());});

    }

    @Override
    public void filterByCategory(String categoryName) {
        filterByCategoryRepo.getCategoryToFilterFromRepo(categoryName).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(item->{

                    searchView.onMealsSuccess(item.meals);
                },error->{
                            searchView.onFailure(error.getMessage());
                });

    }

    @Override
    public void filterByCountry(String countryName) {
        filterAreaRepo.getAreaMealsFromRepo(countryName).
                subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(item->{
                                    searchView.onAreaMealsSuccess(item.meals);
                                },error->{
                                    searchView.onFailure(error.getMessage());
                                        }
                                );

    }

    @Override
    public void filterByIngredient(String ingredientName) {
        filterByIngredientRepo.getIngredientMealsFromRepo(ingredientName).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(item->{
                                    searchView.onMealsSuccess(item.meals);
                                },error->{
                                    searchView.onFailure(error.getMessage());
                                });

    }
}
