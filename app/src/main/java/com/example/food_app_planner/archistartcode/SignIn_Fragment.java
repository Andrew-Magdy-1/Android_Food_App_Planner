package com.example.food_app_planner.archistartcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.presentation.homepage.view.HomePage;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignIn_Fragment extends Fragment {

    private static final int RC_SIGN_IN = 1001;
    private static final String TAG = "SignInFragment";
    MaterialButton materialButton;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private ImageView googleSignInButton;
    private Button goToSignUp;
    private onSignUpClickListener onSignUpClickListener1;

    public SignIn_Fragment() {

    }
    public void setonSignUpClickListener(onSignUpClickListener onSignUpClickListener){
        this.onSignUpClickListener1=onSignUpClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth = FirebaseAuth.getInstance();


        configureGoogleSignIn();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_in_, container, false);
        googleSignInButton = view.findViewById(R.id.google_login_btn);
        materialButton=view.findViewById(R.id.signInButton);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), HomePage.class);
                startActivity(intent);

            }
        });
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // goToSignUp=view.findViewById(R.id.signUp_btn);
//        goToSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(onSignUpClickListener1!=null){
//                    SignUpFragment signUpFragment=new SignUpFragment();
//                    onSignUpClickListener1.onSignUpClickListener(signUpFragment);
//
//                }else {
//                    Toast.makeText(getContext(), "Listener not set", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });


        checkCurrentUser();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onSignUpClickListener) {
            onSignUpClickListener1 = (onSignUpClickListener) context;
        }
    }

    private void configureGoogleSignIn() {
        try {

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.client_id))
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
            Log.d(TAG, "Google Sign-In configured successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error configuring Google Sign-In: " + e.getMessage());
            Toast.makeText(getContext(), "Error setting up Google Sign-In", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkCurrentUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signInWithGoogle() {
        if (googleSignInClient == null) {
            Toast.makeText(getContext(), "Google Sign-In not configured. Please restart app.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null && account.getIdToken() != null) {
                Log.d(TAG, "Google Sign-In successful: " + account.getEmail());
                firebaseAuthWithGoogle(account.getIdToken());
            } else {
                Log.w(TAG, "Google Sign-In failed: account or token is null");
                Toast.makeText(getContext(), "Sign-in failed: No account information",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            Log.w(TAG, "Google Sign-In failed: code=" + e.getStatusCode() + ", message=" + e.getMessage());
            handleGoogleSignInError(e);
        }
    }

    private void handleGoogleSignInError(ApiException e) {
        String errorMessage;
        switch (e.getStatusCode()) {
            case 10: // DEVELOPER_ERROR
                errorMessage = "Developer error: Check Web Client ID configuration";
                break;
            case 12500: // INTERNAL_ERROR
                errorMessage = "Sign-in failed: Configure OAuth consent screen";
                break;
            case 12501: // CANCELLED
                errorMessage = "Sign-in cancelled by user";
                break;
            case 4: // SIGN_IN_REQUIRED
                errorMessage = "Sign-in required";
                break;
            case 7: // NETWORK_ERROR
                errorMessage = "Network error: Check internet connection";
                break;
            default:
                errorMessage = "Error " + e.getStatusCode() + ": " + e.getMessage();
        }

        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, "Firebase authentication successful: " + user.getEmail());
                            Toast.makeText(getContext(), "Welcome " + user.getDisplayName(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // Sign in failed
                            Log.w(TAG, "Firebase authentication failed", task.getException());
                            Toast.makeText(getContext(), "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

            Log.d(TAG, "User signed in: " + user.getEmail());


            if (googleSignInButton != null) {
                googleSignInButton.setVisibility(View.GONE);
            }

        } else {

            Log.d(TAG, "User signed out");
            if (googleSignInButton != null) {
                googleSignInButton.setVisibility(View.VISIBLE);
            }
        }
    }

    // Optional: Sign out method
    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        if (googleSignInClient != null) {
            googleSignInClient.signOut().addOnCompleteListener(requireActivity(),
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            updateUI(null);
                            Toast.makeText(getContext(), "Signed out", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}