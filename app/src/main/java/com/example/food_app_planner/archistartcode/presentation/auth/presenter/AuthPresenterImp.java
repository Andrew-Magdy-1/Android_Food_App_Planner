package com.example.food_app_planner.archistartcode.presentation.auth.presenter;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseManager;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseRemoteDataSource;
import com.example.food_app_planner.archistartcode.presentation.auth.view.AuthView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

public class AuthPresenterImp implements AuthPresenter {

    private static final String TAG = "AuthPresenterImp";
    private AuthView view;
    private final FirebaseRemoteDataSource firebaseDataSource;
    private final FirebaseManager firebaseManager;

    public AuthPresenterImp(AuthView view) {
        this.view = view;
        this.firebaseDataSource = FirebaseRemoteDataSource.getInstance();
        this.firebaseManager = FirebaseManager.getInstance();
    }

    @Override
    public void signInWithEmail(String email, String password) {
        if (!validateEmailPassword(email, password)) {
            return;
        }

        view.clearErrors();
        view.showLoading();

        firebaseDataSource.signInWithEmail(email, password, new FirebaseNetworkResponse() {
            @Override
            public void onSuccess(FirebaseUser user) {
                if (view != null) {
                    view.hideLoading();

                    // ✅ Create/Update user document
//                    firebaseManager.createUserDocument(
//                            user.getUid(),
//                            user.getEmail(),
//                            user.getDisplayName() != null ? user.getDisplayName() : "User",
//                            "email"
//                    );
//
//                    // ✅ Update last login
//                    firebaseManager.updateUserLastLogin(user.getUid());

                    view.showSuccess("Welcome back!");
                    view.navigateToHomePage();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                if (view != null) {
                    view.hideLoading();
                    view.showError(errorMessage);
                }
            }
        });
    }

    @Override
    public void signUpWithEmail(String email, String password, String username) {
        if (!validateSignUp(email, password, username)) {
            return;
        }

        view.clearErrors();
        view.showLoading();

        firebaseDataSource.signUpWithEmail(email, password, new FirebaseNetworkResponse() {
            @Override
            public void onSuccess(FirebaseUser user) {
                if (view != null) {
                    view.hideLoading();

//                    // ✅ Create user document with username
//                    firebaseManager.createUserDocument(
//                            user.getUid(),
//                            email,
//                            username,
//                            "email"
//                    );

                    view.showSuccess("Account created successfully!");
                    view.navigateToHomePage();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                if (view != null) {
                    view.hideLoading();
                    view.showError(errorMessage);
                }
            }
        });
    }

    @Override
    public void signInWithGoogle(String idToken) {

    }

    @Override
    public void onGoogleSignInResult(GoogleSignInAccount account) {
        if (account == null || account.getIdToken() == null) {
            view.showError("Failed to get Google account");
            view.hideLoading();
            return;
        }

        String idToken = account.getIdToken();
        view.showLoading();

        firebaseDataSource.signInWithGoogle(idToken, new FirebaseNetworkResponse() {
            @Override
            public void onSuccess(FirebaseUser user) {
                if (view != null) {
                    view.hideLoading();

                    // ✅ Create/Update user document
//                    firebaseManager.createUserDocument(
//                            user.getUid(),
//                            user.getEmail(),
//                            user.getDisplayName() != null ? user.getDisplayName() : "User",
//                            "google"
//                    );

                    // ✅ Update last login
                    //firebaseManager.updateUserLastLogin(user.getUid());

                    String name = user.getDisplayName() != null ? user.getDisplayName() : "User";
                    view.showSuccess("Welcome, " + name + "!");
                    view.navigateToHomePage();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                if (view != null) {
                    view.hideLoading();
                    view.showError(errorMessage);
                }
            }
        });
    }

    @Override
    public void signInAsGuest() {
        view.showLoading();

        firebaseDataSource.signInAnonymously(new FirebaseNetworkResponse() {
            @Override
            public void onSuccess(FirebaseUser user) {
                if (view != null) {
                    view.hideLoading();

//                    // ✅ Create guest user document
//                    firebaseManager.createUserDocument(
//                            user.getUid(),
//                            "guest@temp.com",
//                            "Guest User",
//                            "guest"
//                    );

                    view.showSuccess("Signed in as guest");
                    view.navigateToHomePage();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                if (view != null) {
                    view.hideLoading();
                    view.showError(errorMessage);
                }
            }
        });
    }

    private boolean validateEmailPassword(String email, String password) {
        boolean isValid = true;

        if (TextUtils.isEmpty(email)) {
            view.setEmailError("Email required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.setEmailError("Invalid email format");
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {
            view.setPasswordError("Password required");
            isValid = false;
        } else if (password.length() < 6) {
            view.setPasswordError("Password must be at least 6 characters");
            isValid = false;
        }

        return isValid;
    }

    private boolean validateSignUp(String email, String password, String username) {
        boolean isValid = validateEmailPassword(email, password);

        if (TextUtils.isEmpty(username)) {
            view.showError("Username required");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}