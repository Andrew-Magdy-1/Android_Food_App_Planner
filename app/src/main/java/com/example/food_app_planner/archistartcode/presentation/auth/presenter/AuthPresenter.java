package com.example.food_app_planner.archistartcode.presentation.auth.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface AuthPresenter {
    void signInWithEmail(String email, String password);
    void signUpWithEmail(String email, String password, String username);
    void signInWithGoogle(String idToken);
    void signInAsGuest();
    void onGoogleSignInResult(GoogleSignInAccount account);
    void onDestroy();
}