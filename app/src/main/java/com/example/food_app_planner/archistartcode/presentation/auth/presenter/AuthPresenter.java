package com.example.food_app_planner.archistartcode.presentation.auth.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface AuthPresenter {
    void signInWithEmail(String email, String password);
    void signInWithGoogle(String idToken);
    void onGoogleSignInResult(GoogleSignInAccount account);
    void onDestroy();
}
