package com.example.food_app_planner.archistartcode.presentation.specificarea.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbyarea.AreaMeals;
import com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.presenter.SpecificCategoryPresenter;
import com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.view.SpecificCategoryAdapter;
import com.example.food_app_planner.archistartcode.presentation.mealbyid.view.OnClickMealListener;
import com.example.food_app_planner.archistartcode.presentation.specificarea.presenter.SpecificAreaPresenter;
import com.example.food_app_planner.archistartcode.presentation.specificarea.presenter.SpecificAreaPresenterImp;

import java.util.List;


public class SpecificAreaFragment extends Fragment implements SpecificAreaView,OnClickAreaListener, OnClickMealListener {
    RecyclerView recyclerView;
    SpecificAreaPresenter specificAreaPresenter;
    SpecificAreaAdapter specificAreaAdapter;
    String areaname;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_specific_area, container, false);
        recyclerView=v.findViewById(R.id.areaRecyclerView);
        specificAreaAdapter=new SpecificAreaAdapter();
        recyclerView.setAdapter(specificAreaAdapter);
        areaname=getArguments().getString("AREA_NAME");
        specificAreaAdapter.setOnClickMealListener(this);
        specificAreaPresenter=new SpecificAreaPresenterImp(getContext(),this,areaname);
        specificAreaPresenter.getAllAreaMeal();


        return v;
    }

    @Override
    public void onSuccess(List<AreaMeals> areaMealsList) {

        Log.d("SpecificArea", "Data received: " + areaMealsList.size());
        specificAreaAdapter.setAreaMealsList(areaMealsList);

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void getAreaName(String areaName) {
        this.areaname=areaName;
    }

    @Override
    public void onClickMeal(String id) {
        Bundle bundle=new Bundle();
        bundle.putString("Meal_id",id);
        NavHostFragment.findNavController(SpecificAreaFragment.this).navigate(R.id.action_specificAreaFragment_to_mealByIdFragment,bundle);

    }
}