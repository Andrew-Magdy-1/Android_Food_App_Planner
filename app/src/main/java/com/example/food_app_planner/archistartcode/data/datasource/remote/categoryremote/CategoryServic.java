package com.example.food_app_planner.archistartcode.data.datasource.remote.categoryremote;

import com.example.food_app_planner.archistartcode.data.datasource.models.category.CategoryResonse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryServic {
    @GET("categories.php")
    Observable<CategoryResonse> getCategories();
}
