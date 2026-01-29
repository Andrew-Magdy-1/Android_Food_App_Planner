package com.example.food_app_planner.archistartcode.presentation.auth.presenter;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.food_app_planner.archistartcode.data.datasource.repositores.firbaserepo.FirebaseRepo;
import com.example.food_app_planner.archistartcode.presentation.auth.view.AuthView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class AuthPresenterImp implements AuthPresenter {
    private FirebaseRepo firebaseRepo;
    private AuthView authView;

    public AuthPresenterImp(AuthView authView) {
        firebaseRepo = new FirebaseRepo();
        this.authView = authView;
    }

    @Override
    public void signInWithEmail(String email, String password) {
        if (!validateInput(email, password)) {
            return;
        }
        authView.clearErrors();
        authView.showLoading();

        firebaseRepo.signInWithEmail(email, password, new FirebaseRepo.AuthCallback() {
            @Override
            public void onSuccess(String message) {
                if (authView != null) {
                    authView.hideLoading();
                    authView.showSuccess(message);
                    authView.navigateToHomePage();
                }
            }

            @Override
            public void onFailure(String error) {
                if (authView != null) {
                    authView.hideLoading();
                    authView.showError(error);
                }
            }
        });
    }

    @Override
    public void signInWithGoogle(String idToken) {
        if (authView != null) {
            authView.showLoading();
        }

        firebaseRepo.signInWithGoogle(idToken, new FirebaseRepo.AuthCallback() {
            @Override
            public void onSuccess(String message) {
                if (authView != null) {
                    authView.hideLoading();
                    authView.showSuccess(message);
                    authView.navigateToHomePage();
                }
            }

            @Override
            public void onFailure(String error) {
                if (authView != null) {
                    authView.hideLoading();
                    authView.showError(error);
                }
            }
        });
    }

    @Override
    public void onGoogleSignInResult(GoogleSignInAccount account) {
        if (account != null && account.getIdToken() != null) {
            signInWithGoogle(account.getIdToken());
        } else {
            if (authView != null) {
                authView.showError("Failed to get account information");
            }
        }
    }

    private boolean validateInput(String email, String password) {
        boolean isValid = true;

        if (TextUtils.isEmpty(email)) {
            if (authView != null) {
                authView.setEmailError("Email required");
            }
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (authView != null) {
                authView.setEmailError("Invalid email format");
            }
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {
            if (authView != null) {
                authView.setPasswordError("Password required");
            }
            isValid = false;
        } else if (password.length() < 6) {
            if (authView != null) {
                authView.setPasswordError("Password must be at least 6 characters");
            }
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void onDestroy() {
        authView = null;
    }
}