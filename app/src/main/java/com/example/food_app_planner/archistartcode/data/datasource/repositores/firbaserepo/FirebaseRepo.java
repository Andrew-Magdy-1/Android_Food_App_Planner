package com.example.food_app_planner.archistartcode.data.datasource.repositores.firbaserepo;

import android.util.Log;

import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseManager;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseRemoteDataSource;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseRepo {
    FirebaseRemoteDataSource firebaseRemoteDataSource;
    FirebaseManager firebaseManager;

    private static final String TAG = "FirebaseRepo";

    public FirebaseRepo() {
        firebaseRemoteDataSource = new FirebaseRemoteDataSource();
        firebaseManager = FirebaseManager.getInstance();
    }

    public interface AuthCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }

    public void signInWithEmail(String email, String password, AuthCallback callback) {
        firebaseRemoteDataSource.signInWithEmail(email, password, new FirebaseNetworkResponse() {
            @Override
            public void onSuccess(FirebaseUser user) {
                syncUserDataAfterLogin(user.getUid());
                String name = user.getDisplayName();
                    if (name == null || name.isEmpty()) {
                        name = user.getEmail().split("@")[0];
                    }

                callback.onSuccess(name);
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }
    public String getCurrentUserName() {
        return firebaseRemoteDataSource.getCurrentUserName();
    }
    public void signUpWithEmail(String email, String password, String username, AuthCallback callback) {
        firebaseRemoteDataSource.signUpWithEmail(email, password, username, new FirebaseNetworkResponse() {
            @Override
            public void onSuccess(FirebaseUser user) {
                if (user != null) {
                    callback.onSuccess("Account created successfully! Welcome, " + username);
                } else {
                    callback.onFailure("User creation failed");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    public void signInWithGoogle(String idToken, AuthCallback callback) {
        firebaseRemoteDataSource.signInWithGoogle(idToken, new FirebaseNetworkResponse() {
            @Override
            public void onSuccess(FirebaseUser user) {
                String name = user.getDisplayName() != null ? user.getDisplayName() : "User";
                String email = user.getEmail();
                String userId = user.getUid();
                firebaseRemoteDataSource.checkIfUserIsNew(user, new FirebaseRemoteDataSource.UserCheckCallback() {
                    @Override
                    public void onResult(boolean isNewUser) {
                        if (isNewUser) {
                            callback.onSuccess("Welcome, " + name + "!");
                        } else {
                            callback.onSuccess("Welcome back, " + name + "!");
                        }
                        syncUserDataAfterLogin(userId);
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    public void signInAnonymously(AuthCallback callback) {
        firebaseRemoteDataSource.signInAnonymously(new FirebaseNetworkResponse() {
            @Override
            public void onSuccess(FirebaseUser user) {

                if(user.isAnonymous()){
                callback.onSuccess("guest");}
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    public void signOut() {
        firebaseRemoteDataSource.signOut();
    }

    public boolean isUserSignedIn() {
        return firebaseRemoteDataSource.isUserSignedIn();
    }

    public boolean isGuestUser() {
        return firebaseRemoteDataSource.isUserSignedIn() &&
                firebaseRemoteDataSource.getCurrentUser() != null &&
                firebaseRemoteDataSource.getCurrentUser().isAnonymous();
    }

    public String getCurrentUserId() {
        FirebaseUser user = firebaseRemoteDataSource.getCurrentUser();
        return user != null ? user.getUid() : null;
    }

    private void syncUserDataAfterLogin(String userId) {
        Log.d(TAG, "Starting data sync for user: " + userId);

    }

}