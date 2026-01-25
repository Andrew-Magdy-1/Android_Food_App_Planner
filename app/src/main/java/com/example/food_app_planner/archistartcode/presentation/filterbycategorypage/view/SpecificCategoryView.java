package com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.view;

import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;

import java.util.List;

public interface SpecificCategoryView {
    void onSuccess(List<CategoryDetails> categoryDetailsList);
    void onFailure(String errrorMessage);
}
