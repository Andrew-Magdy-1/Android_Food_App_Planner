package com.example.food_app_planner.archistartcode.presentation.search.view;

import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
import com.example.food_app_planner.archistartcode.data.datasource.models.countries.Country;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbyarea.AreaMeals;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;
import com.example.food_app_planner.archistartcode.data.datasource.models.ingredient.Ingredient;

import java.util.List;

public interface SearchView {
    void onCategoriesSuccess(List<Category> categoryList);
    void onCountriesSuccess(List<Country> countryList);
    void onIngredientsSuccess(List<Ingredient> ingredientList);
    void onMealsSuccess(List<CategoryDetails> mealList);
    void onAreaMealsSuccess(List<AreaMeals> areaMeals);
    void onFailure(String errorMessage);
}
