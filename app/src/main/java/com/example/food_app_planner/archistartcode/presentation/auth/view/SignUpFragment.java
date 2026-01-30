package com.example.food_app_planner.archistartcode.presentation.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.presentation.auth.presenter.AuthPresenter;
import com.example.food_app_planner.archistartcode.presentation.auth.presenter.AuthPresenterImp;
import com.example.food_app_planner.archistartcode.presentation.homepage.view.HomePage;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpFragment extends Fragment implements AuthView {

    private static final String TAG = "SignUpFragment";
    private static final int RC_SIGN_IN = 1002;

    // Views
    private MaterialButton btnSignUp, btnGuest;
    private TextView tvGoToSignIn;
    private View googleSignInButton;
    private TextInputEditText etUsername, etEmail, etPassword;
    private TextInputLayout usernameLayout, emailLayout, passwordLayout;

    // Presenter
    private AuthPresenter authPresenter;
    private GoogleSignInClient googleSignInClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authPresenter = new AuthPresenterImp(this);
        configureGoogleSignIn();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupClickListeners();
    }

    private void initViews(View view) {
        etUsername = view.findViewById(R.id.usernameInput);
        etEmail = view.findViewById(R.id.emailInput);
        etPassword = view.findViewById(R.id.passwordInput);

        usernameLayout = view.findViewById(R.id.usernameLayout);
        emailLayout = view.findViewById(R.id.emailLayout);
        passwordLayout = view.findViewById(R.id.passwordLayout);

        btnSignUp = view.findViewById(R.id.signUpButton);
        btnGuest = view.findViewById(R.id.guestButton);
        tvGoToSignIn = view.findViewById(R.id.gotoSignin);
        googleSignInButton = view.findViewById(R.id.googleSignInButton);
    }

    private void setupClickListeners() {
        btnSignUp.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            authPresenter.signUpWithEmail(email, password, username);

        });

        googleSignInButton.setOnClickListener(v -> signInWithGoogle());

        btnGuest.setOnClickListener(v -> authPresenter.signInAsGuest());

        tvGoToSignIn.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });
    }

    private void configureGoogleSignIn() {
        try {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.client_id))
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
            Log.d(TAG, "Google Sign-In configured");

        } catch (Exception e) {
            Log.e(TAG, "Error configuring Google Sign-In: " + e.getMessage());
        }
    }

    private void signInWithGoogle() {
        if (googleSignInClient == null) {
            showError("Google Sign-In not available");
            return;
        }

        googleSignInButton.setEnabled(false);
        showLoading();

        googleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                authPresenter.onGoogleSignInResult(account);
            } catch (ApiException e) {
                Log.e(TAG, "Google Sign-In failed: " + e.getStatusCode());
                showError("Google sign in failed");
                hideLoading();
            }
        }
    }
    @Override
    public void showLoading() {
        if (btnSignUp != null) {
            btnSignUp.setEnabled(false);
        }
        if (googleSignInButton != null) {
            googleSignInButton.setEnabled(false);
        }
        if (btnGuest != null) {
            btnGuest.setEnabled(false);
        }
    }

    @Override
    public void hideLoading() {
        if (btnSignUp != null) {
            btnSignUp.setEnabled(true);
        }
        if (googleSignInButton != null) {
            googleSignInButton.setEnabled(true);
        }
        if (btnGuest != null) {
            btnGuest.setEnabled(true);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccess(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHomePage(String name) {
        try {
            Intent intent = new Intent(requireActivity(),
                    HomePage.class);

            intent.putExtra("user_name", name);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        } catch (Exception e) {
            Log.e(TAG, "Navigation failed: " + e.getMessage());
            showError("Cannot navigate to home page");
        }
    }

    @Override
    public void navigateToSignUp() {
        // Not needed here
    }

    @Override
    public void setEmailError(String error) {
        if (emailLayout != null) {
            emailLayout.setError(error);
        }
    }

    @Override
    public void setPasswordError(String error) {
        if (passwordLayout != null) {
            passwordLayout.setError(error);
        }
    }

    @Override
    public void clearErrors() {
        if (usernameLayout != null) {
            usernameLayout.setError(null);
        }
        if (emailLayout != null) {
            emailLayout.setError(null);
        }
        if (passwordLayout != null) {
            passwordLayout.setError(null);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (authPresenter != null) {
            authPresenter.onDestroy();
        }
    }
}