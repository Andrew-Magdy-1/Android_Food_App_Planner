package com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;
import com.example.food_app_planner.archistartcode.presentation.categorypage.view.CategoryPageFragment;
import com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.presenter.SpecificCategoryPresenter;
import com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.presenter.SpecificCategoryPresenterImp;
import com.example.food_app_planner.archistartcode.presentation.mealbyid.view.OnClickMealListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class SpecificCategoryPageFragment extends Fragment implements SpecificCategoryView,OnClickCategoryClick, OnClickMealListener {
    private RecyclerView recyclerView;
    private SpecificCategoryPresenter specificCategoryPresenter;
    private SpecificCategoryAdapter specificCategoryAdapter;
    private String catname;

    public SpecificCategoryPageFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_specific_category_page, container, false);
        recyclerView=v.findViewById(R.id.categoryRecyclerView);
        specificCategoryAdapter=new SpecificCategoryAdapter();
        recyclerView.setAdapter(specificCategoryAdapter);
        catname=getArguments().getString("CATEGORY_NAME");
        specificCategoryAdapter.setOnClickMealListener(this);


       specificCategoryPresenter=new SpecificCategoryPresenterImp(getContext(),this,catname);
       specificCategoryPresenter.getAllCategoryMeals();


        return v;
    }

    @Override
    public void onSuccess(List<CategoryDetails> categoryDetailsList) {
        specificCategoryAdapter.setCategoryDetailsListList(categoryDetailsList);

    }

    @Override
    public void onFailure(String errrorMessage) {

    }

    @Override
    public void onClick(String catName) {
        this.catname=catName;
    }

    @Override
    public void onClickMeal(String id) {
          Bundle bundle=new Bundle();
          bundle.putString("Meal_id",id);
        NavHostFragment.findNavController(SpecificCategoryPageFragment.this)
                .navigate(R.id.action_specificCategoryPageFragment_to_mealByIdFragment, bundle);



    }
}