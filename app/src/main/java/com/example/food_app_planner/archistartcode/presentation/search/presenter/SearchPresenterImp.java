package com.example.food_app_planner.archistartcode.presentation.search.presenter;

import android.content.Context;

import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
import com.example.food_app_planner.archistartcode.data.datasource.models.countries.Country;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbyarea.AreaMeals;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;
import com.example.food_app_planner.archistartcode.data.datasource.models.ingredient.Ingredient;
import com.example.food_app_planner.archistartcode.data.datasource.remote.categoryremote.CategoryNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.countryremote.CountryNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyarearemote.FilterAreaNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbycategory.FilterByCategoryNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyingrediant.FilterByIngredientNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.ingrediantremote.IngredientNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.countryrepo.CountryRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.filterarearepo.FilterAreaRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.filterbycategoryrepo.FilterByCategoryRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.filterbyingredientrepo.FilterByIngredientRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.homerandomandcategories.RandomMealRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.ingredientrepo.IngredientRepo;
import com.example.food_app_planner.archistartcode.presentation.search.view.SearchView;

import java.util.List;

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
        countryRepo.getCatFromRepo(new CategoryNetworkResponse() {
            @Override
            public void onSuccess(List<Category> categoryList) {
                searchView.onCategoriesSuccess(categoryList);

            }

            @Override
            public void onFailure(String errorMessage) {
                searchView.onFailure(errorMessage);

            }
        });
    }

    @Override
    public void getAllCountries() {
        country.getCountriesFromRepo(new CountryNetworkResponse() {
            @Override
            public void onSunccess(List<Country> countryList) {
                searchView.onCountriesSuccess(countryList);
            }

            @Override
            public void onFailure(String errorMessage) {
                searchView.onFailure(errorMessage);
            }
        });
    }

    @Override
    public void getAllIngredients() {
        ingredientRepo.getIngredientsFromRepo(new IngredientNetworkResponse() {
            @Override
            public void onSuccess(List<Ingredient> ingredientList) {
                searchView.onIngredientsSuccess(ingredientList);
            }

            @Override
            public void onFailure(String errorMessage) {
                searchView.onFailure(errorMessage);

            }
        });
    }

    @Override
    public void filterByCategory(String categoryName) {
        filterByCategoryRepo.getCategoryToFilterFromRepo(categoryName, new FilterByCategoryNetworkResponse() {

            @Override
            public void onSuccess(List<CategoryDetails> categoryDetailsList) {
                searchView.onMealsSuccess(categoryDetailsList);

            }

            @Override
            public void onFailure(String errorMessage) {
                searchView.onFailure(errorMessage);
            }
        });
    }

    @Override
    public void filterByCountry(String countryName) {
        filterAreaRepo.getAreaMealsFromRepo(countryName, new FilterAreaNetworkResponse() {

            @Override
            public void onSuccess(List<AreaMeals> areaMealsList) {

                searchView.onAreaMealsSuccess(areaMealsList);

            }

            @Override
            public void onFailure(String errorMessage) {
                searchView.onFailure(errorMessage);
            }
        });
    }

    @Override
    public void filterByIngredient(String ingredientName) {
        filterByIngredientRepo.getIngredientMealsFromRepo(ingredientName, new FilterByIngredientNetworkResponse() {
            @Override
            public void onSunccess(List<CategoryDetails> categoryDetailsList) {
                searchView.onMealsSuccess(categoryDetailsList);
            }

            @Override
            public void onFailure(String errorMessage) {
                searchView.onFailure(errorMessage);
            }
        });
    }
}
