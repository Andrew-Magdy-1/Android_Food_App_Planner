package com.example.food_app_planner.archistartcode.presentation.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.food_app_planner.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignUpFragment extends Fragment {

    private static final String TAG = "SignUpFragment";
    private static final int RC_SIGN_IN = 1002;

    // Views
    private MaterialButton btnSignUp, btnGuest;
    private TextView tvGoToSignIn;
    private View googleSignInButton;
    private TextInputEditText etUsername, etEmail, etPassword;
    private TextInputLayout usernameLayout, emailLayout, passwordLayout;

    // Firebase
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        configureGoogleSignIn();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupClickListeners();
    }

    private void initViews(View view) {
        etUsername = view.findViewById(R.id.usernameInput);
        etEmail = view.findViewById(R.id.emailInput);
        etPassword = view.findViewById(R.id.passwordInput);

        usernameLayout = view.findViewById(R.id.usernameLayout);
        emailLayout = view.findViewById(R.id.emailLayout);
        passwordLayout = view.findViewById(R.id.passwordLayout);

        btnSignUp = view.findViewById(R.id.signUpButton);
        btnGuest = view.findViewById(R.id.guestButton);
        tvGoToSignIn = view.findViewById(R.id.gotoSignin);
        googleSignInButton = view.findViewById(R.id.googleSignInButton);
    }

    private void setupClickListeners() {
        btnSignUp.setOnClickListener(v -> createAccountWithEmail());
        googleSignInButton.setOnClickListener(v -> signInWithGoogle());
        btnGuest.setOnClickListener(v -> continueAsGuest());

        tvGoToSignIn.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });
    }

    private void configureGoogleSignIn() {
        try {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.client_id))
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
            Log.d(TAG, "Google Sign-In configured");

        } catch (Exception e) {
            Log.e(TAG, "Error configuring Google Sign-In: " + e.getMessage());
        }
    }

    private void signInWithGoogle() {
        if (googleSignInClient == null) {
            Toast.makeText(getContext(), "Google Sign-In not available", Toast.LENGTH_SHORT).show();
            return;
        }

        googleSignInButton.setEnabled(false);

        googleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null && account.getIdToken() != null) {
                    Log.d(TAG, "Google Sign-In: " + account.getEmail());
                    firebaseAuthWithGoogle(account.getIdToken());
                }
            } catch (ApiException e) {
                Log.e(TAG, "Google Sign-In failed: " + e.getStatusCode());
                Toast.makeText(getContext(), "Google sign in failed", Toast.LENGTH_SHORT).show();
                googleSignInButton.setEnabled(true);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    boolean isNewUser = authResult.getAdditionalUserInfo() != null &&
                            authResult.getAdditionalUserInfo().isNewUser();

                    String name = mAuth.getCurrentUser() != null &&
                            mAuth.getCurrentUser().getDisplayName() != null
                            ? mAuth.getCurrentUser().getDisplayName()
                            : "User";

                    if (isNewUser) {
                        Log.d(TAG, "✅ New user created with Google");
                        Toast.makeText(getContext(), "Welcome, " + name + "! Account created.", Toast.LENGTH_SHORT).show();
                        navigateToHomePage();

                    } else {
                        // ❌ حساب قديم - في صفحة Sign Up
                        Log.d(TAG, "❌ Account already exists");

                        // اعمل Sign Out
                        mAuth.signOut();
                        googleSignInClient.signOut();

                        // اعرض رسالة
                        Toast.makeText(getContext(),
                                "Account already exists! Please sign in instead.",
                                Toast.LENGTH_LONG).show();

                        // ارجع للشاشة السابقة (Sign In)
                        if (getActivity() != null) {
                            getActivity().onBackPressed();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Firebase auth failed: " + e.getMessage());
                    Toast.makeText(getContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                    googleSignInButton.setEnabled(true);
                });
    }


    private void createAccountWithEmail() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate
        if (TextUtils.isEmpty(username)) {
            usernameLayout.setError("Username required");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            emailLayout.setError("Email required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Password required");
            return;
        }

        usernameLayout.setError(null);
        emailLayout.setError(null);
        passwordLayout.setError(null);
        btnSignUp.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Log.d(TAG, "Account created: " + email);
                    Toast.makeText(getContext(), "Welcome, " + username + "!", Toast.LENGTH_SHORT).show();
                    navigateToHomePage();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Sign up failed: " + e.getMessage());
                    Toast.makeText(getContext(), "Sign up failed", Toast.LENGTH_SHORT).show();
                    btnSignUp.setEnabled(true);
                });
    }


    private void continueAsGuest() {
        btnGuest.setEnabled(false);

        mAuth.signInAnonymously()
                .addOnSuccessListener(authResult -> {
                    Log.d(TAG, "Guest sign in successful");
                    Toast.makeText(getContext(), "Signed in as guest", Toast.LENGTH_SHORT).show();
                    navigateToHomePage();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Guest sign in failed: " + e.getMessage());
                    Toast.makeText(getContext(), "Guest login failed", Toast.LENGTH_SHORT).show();
                    btnGuest.setEnabled(true);
                });
    }


    private void navigateToHomePage() {
        try {
            Intent intent = new Intent(requireActivity(),
                    com.example.food_app_planner.archistartcode.presentation.homepage.view.HomePage.class);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        } catch (Exception e) {
            Log.e(TAG, "Navigation failed: " + e.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        googleSignInButton = null;
    }
}