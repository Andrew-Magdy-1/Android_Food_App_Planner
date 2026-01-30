package com.example.food_app_planner.archistartcode.presentation.search.presenter;

public interface SearchPresenter {
    void getAllCategories();
    void getAllCountries();
    void getAllIngredients();
    void filterByCategory(String categoryName);
    void filterByCountry(String countryName);
    void filterByIngredient(String ingredientName);
}
