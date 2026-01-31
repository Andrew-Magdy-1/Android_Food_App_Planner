package com.example.food_app_planner.archistartcode.presentation.homepage.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.database.calendermeal.CalenderMealDataBase;
import com.example.food_app_planner.archistartcode.database.favourits.AddMealToDb;
import com.example.food_app_planner.archistartcode.network.offlineconnection.NetworkMonitor;
import com.example.food_app_planner.archistartcode.presentation.auth.view.AuthActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {
    private TextView textView;
    private ImageButton logoutButton;
    private NetworkMonitor networkMonitor;
    private ConstraintLayout noInternetLayout;
    private View homeFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        textView = findViewById(R.id.userName);
        logoutButton = findViewById(R.id.logoutButton);
        noInternetLayout = findViewById(R.id.noInternetLayout);
        homeFragmentContainer = findViewById(R.id.homeFragmentContainer);

        Intent intent = getIntent();
        textView.setText(intent.getStringExtra("user_name") + " \uD83D\uDE0A");

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.homeFragmentContainer);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNav, navController);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.favouriteIconFragment || id == R.id.plannerIconFragment) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null &&
                        FirebaseAuth.getInstance().getCurrentUser().isAnonymous()) {
                    showRegisterRequiredDialog();
                    return false;
                }
            }
            return NavigationUI.onNavDestinationSelected(item, navController);
        });

        logoutButton.setOnClickListener(v -> showLogoutDialog());

        // Setup Network Monitor
        setupNetworkMonitor();
    }

    private void setupNetworkMonitor() {
        networkMonitor = new NetworkMonitor(this, new NetworkMonitor.NetworkCallback() {
            @Override
            public void onNetworkAvailable() {
                runOnUiThread(() -> {
                    // إخفاء شاشة No Internet
                    noInternetLayout.setVisibility(View.GONE);
                    homeFragmentContainer.setVisibility(View.VISIBLE);
                });
            }

            @Override
            public void onNetworkUnavailable() {
                runOnUiThread(() -> {
                    // إظهار شاشة No Internet
                    noInternetLayout.setVisibility(View.VISIBLE);
                    homeFragmentContainer.setVisibility(View.GONE);
                    Toast.makeText(HomePage.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                });
            }
        });
        networkMonitor.register();
    }

    private void showLogoutDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout? \uD83E\uDD79")
                .setPositiveButton("Logout", (dialog, which) -> performLogout())
                .setNegativeButton("Stay", null)
                .setBackground(getResources().getDrawable(R.drawable.dialog_background, getTheme()))
                .show();
    }

    private void performLogout() {
        try {
            FirebaseAuth.getInstance().signOut();

            new Thread(() -> {
                CalenderMealDataBase dataBase = CalenderMealDataBase.getInstanse(this);
                AddMealToDb mealToDb = AddMealToDb.getInstance(this);
                dataBase.clearAllTables();
                mealToDb.clearAllTables();
            }).start();

            Intent intent = new Intent(this, AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Logout failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showRegisterRequiredDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Login")
                .setMessage("Please login to use this feature 🥹?")
                .setPositiveButton("Logout", (dialog, which) -> performLogout())
                .setNegativeButton("Stay", null)
                .setBackground(getResources().getDrawable(R.drawable.dialog_background, getTheme()))
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (networkMonitor != null) {
            networkMonitor.register();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (networkMonitor != null) {
            networkMonitor.unregister();
        }
    }
}