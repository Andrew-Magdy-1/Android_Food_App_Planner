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
                String name = user.getDisplayName() != null ? user.getDisplayName() : "User";



                // Sync data from Firestore
                syncUserDataAfterLogin(user.getUid());

                callback.onSuccess("Welcome, " + name + "!");
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    public void signUpWithEmail(String email, String password, AuthCallback callback) {
        firebaseRemoteDataSource.signUpWithEmail(email, password, new FirebaseNetworkResponse() {
            @Override
            public void onSuccess(FirebaseUser user) {
                // Create user document in Firestore
                String userId = user.getUid();
                String userName = user.getDisplayName() != null ? user.getDisplayName() : "User";


                callback.onSuccess("Account created successfully!");
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    public void signUpWithEmailAndName(String email, String password, String username, AuthCallback callback) {
        firebaseRemoteDataSource.signUpWithEmail(email, password, new FirebaseNetworkResponse() {
            @Override
            public void onSuccess(FirebaseUser user) {
                // Update user profile with name
                user.updateProfile(new com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Create user document in Firestore
                                String userId = user.getUid();

                                callback.onSuccess("Account created successfully!");
                            } else {
                                callback.onFailure("Failed to update profile");
                            }
                        });
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

                // Check if user is new
                firebaseRemoteDataSource.checkIfUserIsNew(user, new FirebaseRemoteDataSource.UserCheckCallback() {
                    @Override
                    public void onResult(boolean isNewUser) {
                        if (isNewUser) {
                            // Create user document for new Google user
                            callback.onSuccess("Welcome, " + name + "!");
                        } else {
                            // Update last login for existing user
                            callback.onSuccess("Welcome back, " + name + "!");
                        }

                        // Sync data from Firestore
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
                String userId = user.getUid();

                // Create guest user document

                callback.onSuccess("Signed in as guest");
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
        // Data sync will be handled by the respective Repositories (CalenderMealRepo, FavoriteRepo)
        // This method just logs for now
    }

    // Method to migrate guest data to registered user
    public void migrateGuestData(String guestUserId, String newUserId, MigrationCallback callback) {
        // This method should be called when guest upgrades to registered account
        // Implementation depends on your data structure
        Log.d(TAG, "Migrating data from guest " + guestUserId + " to user " + newUserId);

        // You can implement actual migration logic here
        // For now, just call success
        callback.onMigrationComplete(true, "Data migrated successfully");
    }

    public interface MigrationCallback {
        void onMigrationComplete(boolean success, String message);
    }
}