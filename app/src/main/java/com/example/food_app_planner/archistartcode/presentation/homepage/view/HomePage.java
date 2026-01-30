package com.example.food_app_planner.archistartcode.presentation.homepage.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMeal;
import com.example.food_app_planner.archistartcode.presentation.categorypage.view.CategoryPageFragment;
import com.example.food_app_planner.archistartcode.presentation.categorypage.view.CategoryPageFragmentAdapter;
import com.example.food_app_planner.archistartcode.presentation.countrypage.view.CountryPageFragment;
import com.example.food_app_planner.archistartcode.presentation.homepage.presenter.HomePagePresenter;
import com.example.food_app_planner.archistartcode.presentation.homepage.presenter.HomePagePresenterImp;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class HomePage extends AppCompatActivity {
    HomePageFragment homePageFragment;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        textView=findViewById(R.id.userName);
        Intent intent = getIntent();

        textView.setText(intent.getStringExtra("user_name"));

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.homeFragmentContainer);

        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNav, navController);

    }
}




