package com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseUser;

public interface FirebaseNetworkResponse {
    void onSuccess(FirebaseUser user);
    void onFailure(String errorMessage);

}
