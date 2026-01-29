package com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth;

import android.text.TextUtils;
import android.widget.Toast;


import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class FirebaseRemoteDataSource implements FirebaseService {
    private final FirebaseAuth auth;

    private static FirebaseRemoteDataSource instance;
    public FirebaseRemoteDataSource(){
        this.auth=FirebaseAuth.getInstance();
    }
    public static synchronized FirebaseRemoteDataSource getInstance(){
        if (instance==null)instance=FirebaseRemoteDataSource.getInstance();

        return instance;
    }


    @Override
    public void signInWithEmail(String email, String password, FirebaseNetworkResponse callback) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = authResult.getUser();
                    callback.onSuccess(user);
                })
                .addOnFailureListener(e -> {
                    String errorMessage = e.getMessage();
                    callback.onFailure(errorMessage);
                });
    }

    @Override
    public void signUpWithEmail(String email, String password, FirebaseNetworkResponse callback) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user=authResult.getUser();
                    callback.onSuccess(user);
                }).addOnFailureListener(e -> {
                    callback.onFailure(e.getMessage());
                });

    }

    @Override
    public void signInWithGoogle(String idToken, FirebaseNetworkResponse callback) {
        AuthCredential authCredential= GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(authCredential)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user=authResult.getUser();
                    callback.onSuccess(user);
                }).addOnFailureListener(e -> {
                   callback.onFailure(e.getMessage());
                });

    }

    @Override
    public void signInAnonymously(FirebaseNetworkResponse callback) {

        auth.signInAnonymously()
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = authResult.getUser();
                    callback.onSuccess(user);
                })
                .addOnFailureListener(e -> {
                    callback.onFailure("Guest login failed");
                });

    }

    @Override
    public void signOut() {
        auth.signOut();

    }

    @Override
    public boolean isUserSignedIn() {
        FirebaseUser user=auth.getCurrentUser();
        return user!=null&&!user.isAnonymous();
    }
}
