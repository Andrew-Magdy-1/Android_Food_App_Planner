package com.example.food_app_planner.archistartcode.presentation.auth.view;

public interface AuthView {
    void showLoading();
    void hideLoading();
    void showError(String message);
    void showSuccess(String message);
    void navigateToHomePage(String name);
    void navigateToSignUp();
    void setEmailError(String error);
    void setPasswordError(String error);
    void clearErrors();
}
