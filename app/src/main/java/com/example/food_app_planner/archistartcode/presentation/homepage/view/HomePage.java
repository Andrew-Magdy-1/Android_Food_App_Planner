package com.example.food_app_planner.archistartcode.presentation.homepage.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.airbnb.lottie.LottieAnimationView;
import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.database.calendermeal.CalenderMealDataBase;
import com.example.food_app_planner.archistartcode.database.favourits.AddMealToDb;
import com.example.food_app_planner.archistartcode.network.offlineconnection.NetworkMonitor;
import com.example.food_app_planner.archistartcode.presentation.auth.view.AuthActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {
    private TextView textView;
    private BottomNavigationView bottomNav;
    private NetworkMonitor networkMonitor;
    private ConstraintLayout contentContainer;
    private ConstraintLayout noInternetView;
    private LottieAnimationView lottieAnimationView;
    private MaterialButton btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        bottomNav = findViewById(R.id.bottomNav);
        textView = findViewById(R.id.userName);
        contentContainer = findViewById(R.id.contentContainer);
        noInternetView = findViewById(R.id.noInternetView);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);
        btnRetry = findViewById(R.id.btnRetry);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("user_name");
        if (userName != null) {
            textView.setText(userName);
        }
        setupNavigation();


        btnRetry.setOnClickListener(v -> checkNetworkAndUpdateUI());


        initializeNetworkMonitor();
    }

    private void setupNavigation() {
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

        findViewById(R.id.logoutButton).setOnClickListener(v -> showLogoutDialog());
    }

    private void initializeNetworkMonitor() {
        networkMonitor = new NetworkMonitor(this, new NetworkMonitor.NetworkCallback() {
            @Override
            public void onNetworkAvailable() {
                runOnUiThread(() -> {
                    showContent();
                    Toast.makeText(HomePage.this, "Internet connection restored",
                            Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onNetworkUnavailable() {
                runOnUiThread(() -> {
                    hideContent();
                });
            }
        });
    }

    private void showContent() {
        contentContainer.setVisibility(View.VISIBLE);
        noInternetView.setVisibility(View.GONE);
        bottomNav.setEnabled(true);
        findViewById(R.id.logoutButton).setEnabled(true);
    }

    private void hideContent() {
        contentContainer.setVisibility(View.GONE);
        noInternetView.setVisibility(View.VISIBLE);
        bottomNav.setEnabled(false);
        findViewById(R.id.logoutButton).setEnabled(false);
        if (!lottieAnimationView.isAnimating()) {
            lottieAnimationView.playAnimation();
        }
    }

    private void checkNetworkAndUpdateUI() {
        if (networkMonitor.isNetworkAvailable()) {
            showContent();
            Toast.makeText(this, "Connected to internet", Toast.LENGTH_SHORT).show();
        } else {
            lottieAnimationView.cancelAnimation();
            lottieAnimationView.playAnimation();
            Toast.makeText(this, "Still no internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (networkMonitor != null) {
            networkMonitor.register();
        }
        if (networkMonitor != null && !networkMonitor.isNetworkAvailable()) {
            hideContent();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (networkMonitor != null) {
            networkMonitor.unregister();
        }
        if (lottieAnimationView.isAnimating()) {
            lottieAnimationView.cancelAnimation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // إعادة تشغيل الأنيميشن إذا كان المحتوى مخفياً
        if (noInternetView.getVisibility() == View.VISIBLE &&
                !lottieAnimationView.isAnimating()) {
            lottieAnimationView.playAnimation();
        }
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
                .setPositiveButton("Login", (dialog, which) -> performLogin())
                .setNegativeButton("Stay", null)
                .setBackground(getResources().getDrawable(R.drawable.dialog_background, getTheme()))
                .show();
    }
    private void performLogin() {
        Intent intent = new Intent(this, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}