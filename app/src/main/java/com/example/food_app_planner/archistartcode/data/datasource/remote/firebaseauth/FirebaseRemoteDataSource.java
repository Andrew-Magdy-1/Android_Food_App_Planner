package com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth;

import android.util.Log;

import com.example.food_app_planner.archistartcode.network.Network;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class FirebaseRemoteDataSource {
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "FirebaseRemoteDataSource";
    private static FirebaseRemoteDataSource instanse=null;



    public FirebaseRemoteDataSource() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static FirebaseRemoteDataSource getInstance(){
        if(instanse==null)instanse=new FirebaseRemoteDataSource();
        return instanse;
    }
    public void signInWithEmail(String email, String password, FirebaseNetworkResponse response) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            Log.d(TAG, "Sign in successful: " + user.getEmail());
                            response.onSuccess(user);
                        } else {
                            response.onFailure("User not found");
                        }
                    } else {
                        Log.e(TAG, "Sign in failed: " + task.getException().getMessage());
                        response.onFailure("Sign in failed: " + task.getException().getMessage());
                    }
                });
    }

    public void signUpWithEmail(String email, String password, FirebaseNetworkResponse response) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            Log.d(TAG, "Sign up successful: " + user.getEmail());
                            response.onSuccess(user);
                        } else {
                            response.onFailure("User creation failed");
                        }
                    } else {
                        Log.e(TAG, "Sign up failed: " + task.getException().getMessage());
                        response.onFailure("Sign up failed: " + task.getException().getMessage());
                    }
                });
    }

    public void signInWithGoogle(String idToken, FirebaseNetworkResponse response) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            Log.d(TAG, "Google sign in successful: " + user.getEmail());
                            response.onSuccess(user);
                        } else {
                            response.onFailure("User not found");
                        }
                    } else {
                        Log.e(TAG, "Google sign in failed: " + task.getException().getMessage());
                        response.onFailure("Google sign in failed: " + task.getException().getMessage());
                    }
                });
    }

    public void signInAnonymously(FirebaseNetworkResponse response) {
        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            Log.d(TAG, "Anonymous sign in successful");
                            response.onSuccess(user);
                        } else {
                            response.onFailure("Anonymous user creation failed");
                        }
                    } else {
                        Log.e(TAG, "Anonymous sign in failed: " + task.getException().getMessage());
                        response.onFailure("Anonymous sign in failed: " + task.getException().getMessage());
                    }
                });
    }

    public void signOut() {
        firebaseAuth.signOut();
        Log.d(TAG, "User signed out");
    }

    public boolean isUserSignedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public void checkIfUserIsNew(FirebaseUser user, UserCheckCallback callback) {
        // In Firebase, we can check additional user info
        // This is a simplified version
        user.reload().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Check if user metadata indicates new user
                // This logic might need adjustment based on your needs
                long creationTime = user.getMetadata().getCreationTimestamp();
                long lastSignInTime = user.getMetadata().getLastSignInTimestamp();

                boolean isNewUser = (creationTime == lastSignInTime);
                callback.onResult(isNewUser);
            } else {
                // Default to assuming it's an existing user
                callback.onResult(false);
            }
        });
    }

    public interface UserCheckCallback {
        void onResult(boolean isNewUser);
    }
}