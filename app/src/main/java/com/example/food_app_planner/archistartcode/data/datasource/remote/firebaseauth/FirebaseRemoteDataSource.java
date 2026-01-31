package com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth;

import android.util.Log;

import com.google.firebase.auth.AuthCredential;
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
                            user.reload().addOnCompleteListener(reloadTask -> {
                                response.onSuccess(firebaseAuth.getCurrentUser());
                            });
                        } else {
                            response.onFailure("User not found");
                        }
                    } else {
                        Log.e(TAG, "Sign in failed: " + task.getException().getMessage());
                        response.onFailure("Sign in failed: " + task.getException().getMessage());
                    }
                });
    }

    public void signUpWithEmail(String email, String password, String username, FirebaseNetworkResponse response) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            user.updateProfile(new com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                            .setDisplayName(username)
                                            .build())
                                    .addOnCompleteListener(profileUpdateTask -> {
                                        if (profileUpdateTask.isSuccessful()) {
                                            Log.d(TAG, "Sign up successful: " + user.getEmail() + " " + user.getDisplayName());
                                            response.onSuccess(user);
                                        } else {
                                            Log.e(TAG, "Failed to set display name");
                                            response.onFailure("Failed to set display name");
                                        }
                                    });
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
                        if (user != null && user.isAnonymous()) {
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
    public String getCurrentUserName() {
        return firebaseAuth.getCurrentUser().getDisplayName();
    }

    public void checkIfUserIsNew(FirebaseUser user, UserCheckCallback callback) {

        user.reload().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                long creationTime = user.getMetadata().getCreationTimestamp();
                long lastSignInTime = user.getMetadata().getLastSignInTimestamp();

                boolean isNewUser = (creationTime == lastSignInTime);
                callback.onResult(isNewUser);
            } else {
                callback.onResult(false);
            }
        });
    }

    public interface UserCheckCallback {
        void onResult(boolean isNewUser);
    }
}