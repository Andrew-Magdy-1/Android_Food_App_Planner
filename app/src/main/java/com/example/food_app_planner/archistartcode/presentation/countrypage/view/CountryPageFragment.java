package com.example.food_app_planner.archistartcode.presentation.countrypage.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.countries.Country;
import com.example.food_app_planner.archistartcode.presentation.countrypage.presenter.CountryPagePresenter;
import com.example.food_app_planner.archistartcode.presentation.countrypage.presenter.CountryPagePresenterImp;
import com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.view.SpecificCategoryPageFragment;
import com.example.food_app_planner.archistartcode.presentation.specificarea.view.OnClickAreaListener;

import java.util.List;


public class CountryPageFragment extends Fragment implements CountryPageView, OnClickAreaListener {
   private CountryPagePresenter countryPagePresenter;
   private RecyclerView recyclerView;
   private CountryPageAdapter countryPageAdapter;
    public CountryPageFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_country_page, container, false);

        recyclerView=v.findViewById(R.id.countryRecyclerView);
        countryPageAdapter=new CountryPageAdapter(getContext());
        recyclerView.setAdapter(countryPageAdapter);
        countryPageAdapter.setOnClickAreaListener(this);
        countryPagePresenter=new CountryPagePresenterImp(getContext(),this);
        countryPagePresenter.getAllAreas();

        return v;
    }

    @Override
    public void onSuccess(List<Country> countryList) {
        countryPageAdapter.setCountryList(countryList);

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void getAreaName(String areaName) {
        Bundle bundle=new Bundle();
        bundle.putString("AREA_NAME",areaName);
        SpecificCategoryPageFragment fragment = new SpecificCategoryPageFragment();
        NavHostFragment.findNavController(CountryPageFragment.this)
                .navigate(R.id.action_countrypage_to_specificAreaFragment, bundle);

    }
}