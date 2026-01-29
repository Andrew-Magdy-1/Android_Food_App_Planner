package com.example.food_app_planner.archistartcode.data.datasource.repositores.firbaserepo;

import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseRemoteDataSource;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseRepo {
    FirebaseRemoteDataSource firebaseRemoteDataSource;
    public FirebaseRepo(){
        firebaseRemoteDataSource=new FirebaseRemoteDataSource();
    }
    public interface AuthCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }
    public void signInWithEmail(String email, String password, AuthCallback callback) {
        firebaseRemoteDataSource.signInWithEmail(email, password, new FirebaseNetworkResponse() {
            @Override
            public void onSuccess(FirebaseUser user) {
                String name = user.getDisplayName() != null ? user.getDisplayName() : "User";
                callback.onSuccess("Welcome, " + name + "!");
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    // Sign Up with Email
    public void signUpWithEmail(String email, String password, AuthCallback callback) {
        firebaseRemoteDataSource.signUpWithEmail(email, password, new FirebaseNetworkResponse() {
            @Override
            public void onSuccess(FirebaseUser user) {
                callback.onSuccess("Account created successfully!");
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
                callback.onSuccess("Welcome, " + name + "!");
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    // Sign In Anonymously
    public void signInAnonymously(AuthCallback callback) {
        firebaseRemoteDataSource.signInAnonymously(new FirebaseNetworkResponse() {
            @Override
            public void onSuccess(FirebaseUser user) {
                callback.onSuccess("Signed in as guest");
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    // Sign Out
    public void signOut() {
        firebaseRemoteDataSource.signOut();
    }

    // Check if user is signed in
    public boolean isUserSignedIn() {
        return firebaseRemoteDataSource.isUserSignedIn();
    }
}
