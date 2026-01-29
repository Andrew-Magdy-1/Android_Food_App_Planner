package com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth;

public interface FirebaseService {


    void signInWithEmail(String email, String password, FirebaseNetworkResponse callback);
    void signUpWithEmail(String email, String password, FirebaseNetworkResponse callback);
    void signInWithGoogle(String idToken, FirebaseNetworkResponse callback);
    void signInAnonymously(FirebaseNetworkResponse callback);
    void signOut();
    boolean isUserSignedIn();
}