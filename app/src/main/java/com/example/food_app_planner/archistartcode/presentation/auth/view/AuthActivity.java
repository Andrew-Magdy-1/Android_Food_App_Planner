package com.example.food_app_planner.archistartcode.presentation.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseRemoteDataSource;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.firbaserepo.FirebaseRepo;
import com.example.food_app_planner.archistartcode.presentation.homepage.view.HomePage;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private NavController navController;
    private FirebaseRepo firebaseRepo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        firebaseRepo = new FirebaseRepo();
        FirebaseRemoteDataSource dataSource = FirebaseRemoteDataSource.getInstance();
        if (dataSource.isUserSignedIn()) {
            Intent intent = new Intent(this, HomePage.class);
            String name = "User";
            if (dataSource.getCurrentUser() != null && dataSource.getCurrentUser().getDisplayName() != null) {
                name = dataSource.getCurrentUser().getDisplayName();
            }
            intent.putExtra("user_name", name);
            startActivity(intent);
            finish();
            return;
        }
       // setContentView(R.layout.activity_auth);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.nav_host_fragment, new SignIn_Fragment())
//                    .commit();
//        }
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();

        setupNavigation();

        //syncUserData();
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
//    private void syncUserData() {
//        FirebaseManager firebaseManager = FirebaseManager.getInstance();
//
//        if (firebaseManager.isUserLoggedIn()) {
//            MealLocalDataSource mealLocalDataSource = new MealLocalDataSource(this);
//            mealLocalDataSource.forceSyncFromFirestore();
//         //   CalenderMealDataSource calenderDataSource = new CalenderMealDataSource(this);
//            long start = System.currentTimeMillis();
//            long end = start + (30L * 24 * 60 * 60 * 1000);
//        }
//    }
    @Override
    public boolean onSupportNavigateUp() {
        if (navController != null) {
            return navController.navigateUp() || super.onSupportNavigateUp();
        }
        return super.onSupportNavigateUp();
    }
    private void navigateToHome(String name) {
        Intent intent = new Intent(this, AuthActivity.class);
        intent.putExtra("USER_NAME", name);
        startActivity(intent);
        finish();
    }

}