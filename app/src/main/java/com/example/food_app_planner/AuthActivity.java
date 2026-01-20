package com.example.food_app_planner;

import android.os.Bundle;

import android.widget.Button;


import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AuthActivity extends AppCompatActivity implements onSignUpClickListener{
    Button signup;
    FragmentManager manager;
    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
        //SignIn_Fragment signInFragment=new SignIn_Fragment();
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        if(savedInstanceState==null){

            SignIn_Fragment signInFragment = new SignIn_Fragment();

        }






    }


    @Override
    public void onSignUpClickListener(Fragment f) {
        transaction.add(R.id.fragmentContainerView3,f);
        transaction.commit();

    }
}