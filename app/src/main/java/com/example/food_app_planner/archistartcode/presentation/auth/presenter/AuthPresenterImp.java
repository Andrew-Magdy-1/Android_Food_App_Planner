package com.example.food_app_planner.archistartcode.presentation.auth.presenter;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseManager;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseRemoteDataSource;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.firbaserepo.FirebaseRepo;
import com.example.food_app_planner.archistartcode.presentation.auth.view.AuthView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

public class AuthPresenterImp implements AuthPresenter {

    private static final String TAG = "AuthPresenterImp";
    private AuthView view;
    private final FirebaseRemoteDataSource firebaseDataSource;
    private final FirebaseManager firebaseManager;
    private FirebaseRepo firebaseRepo;

    public AuthPresenterImp(AuthView view) {
        this.view = view;
        this.firebaseDataSource = FirebaseRemoteDataSource.getInstance();
        this.firebaseManager = FirebaseManager.getInstance();
        firebaseRepo=new FirebaseRepo();
    }

    @Override
    public void signInWithEmail(String email, String password) {
        if (!validateEmailPassword(email, password)) {
            return;
        }
        view.clearErrors();
        view.showLoading();
        firebaseRepo.signInWithEmail(email, password, new FirebaseRepo.AuthCallback() {
            @Override
            public void onSuccess(String message) {
                if (view != null) {
                    view.hideLoading();
                    view.showSuccess(message);
                    view.navigateToHomePage(message);
                }

            }

            @Override
            public void onFailure(String error) {

                if (view != null) {
                    view.hideLoading();
                    view.showError(error);
                }

            }
        });


    }

    @Override
    public void signUpWithEmail(String email, String password, String username) {
        if (!validateSignUp(email, password, username)) return;

        view.clearErrors();
        view.showLoading();

        firebaseRepo.signUpWithEmail(email, password, username, new FirebaseRepo.AuthCallback() {
            @Override
            public void onSuccess(String message) {
                if (view != null) {
                    view.hideLoading();
                    view.showSuccess(message);
                    view.navigateToHomePage(username);
                }
            }

            @Override
            public void onFailure(String error) {
                if (view != null) {
                    view.hideLoading();
                    view.showError(error);
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
//        view.showLoading();
//        firebaseRepo.signInWithGoogle(idToken, new FirebaseRepo.AuthCallback() {
//            @Override
//            public void onSuccess(String message) {
//                if (view != null) {
//                    view.hideLoading();
//
//                 //   String name = user.getDisplayName() != null ? user.getDisplayName() : "User";
//                    view.showSuccess("Welcome, " + name + "!");
//                    view.navigateToHomePage(user.getDisplayName());
//                }
//
//            }
//
//            @Override
//            public void onFailure(String error) {
//
//            }
//        });
//

        firebaseDataSource.signInWithGoogle(idToken, new FirebaseNetworkResponse() {
            @Override
            public void onSuccess(FirebaseUser user) {
                if (view != null) {
                    view.hideLoading();

                    String name = user.getDisplayName() != null ? user.getDisplayName() : "User";
                    view.showSuccess("Welcome, " + name + "!");
                    view.navigateToHomePage(user.getDisplayName());
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

                    view.showSuccess("Signed in as guest");
                    view.navigateToHomePage(user.getDisplayName());
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