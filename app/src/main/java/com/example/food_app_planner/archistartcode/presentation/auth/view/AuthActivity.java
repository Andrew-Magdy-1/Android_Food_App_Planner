package com.example.food_app_planner.archistartcode.presentation.auth.view;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.local.calendermeal.CalenderMealDataSource;
import com.example.food_app_planner.archistartcode.data.datasource.local.mealtofavourite.MealLocalDataSource;
import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
import com.example.food_app_planner.archistartcode.data.datasource.models.countries.Country;
import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMeal;
import com.example.food_app_planner.archistartcode.data.datasource.remote.categoryremote.CategoryNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.countryremote.CountryNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseManager;
import com.example.food_app_planner.archistartcode.data.datasource.remote.randommealremote.RandomMealNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.countryrepo.CountryRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.homerandomandcategories.RandomMealRepo;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        setupNavigation();
        syncUserData();
    }
    private void setupNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        } else {
            Log.e("AuthActivity", "NavHostFragment not found!");
        }
    }
    private void syncUserData() {
        FirebaseManager firebaseManager = FirebaseManager.getInstance();

        if (firebaseManager.isUserLoggedIn()) {
            // Sync favorites
            MealLocalDataSource mealLocalDataSource = new MealLocalDataSource(this);
            mealLocalDataSource.forceSyncFromFirestore();

            // Sync calendar meals
            CalenderMealDataSource calenderDataSource = new CalenderMealDataSource(this);
            long start = System.currentTimeMillis();
            long end = start + (30L * 24 * 60 * 60 * 1000); // Next 30 days
            //calenderDataSource.forceSyncFromFirestore(start, end);

            Log.d("HomePage", "✅ User data synced");
        }
    }




    @Override
    public boolean onSupportNavigateUp() {
        if (navController != null) {
            return navController.navigateUp() || super.onSupportNavigateUp();
        }
        return super.onSupportNavigateUp();
    }
}