package com.example.food_app_planner.archistartcode.presentation.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseManager;
import com.example.food_app_planner.archistartcode.presentation.auth.presenter.AuthPresenter;
import com.example.food_app_planner.archistartcode.presentation.auth.presenter.AuthPresenterImp;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignIn_Fragment extends Fragment implements AuthView {

    private static final int RC_SIGN_IN = 1001;
    private static final String TAG = "SignInFragment";
    private AuthPresenter authPresenter;

    private MaterialButton btnSignIn;
    private View googleButtonView;
    private TextInputEditText etEmail, etPassword;
    private TextInputLayout emailLayout, passwordLayout;
    private GoogleSignInClient googleSignInClient;
    private TextView goToSignUp;
    private FirebaseManager firebaseManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseManager = FirebaseManager.getInstance();

        // Check if user is already logged in
        if (firebaseManager.isUserLoggedIn()) {
          //  startMainActivity();
            return;
        }
        authPresenter = new AuthPresenterImp(this);
        configureGoogleSignIn();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in_, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup click listeners
        setupClickListeners();
    }

    private void initViews(View view) {
        etEmail = view.findViewById(R.id.emailInput);
        etPassword = view.findViewById(R.id.passwordInput);
        emailLayout = view.findViewById(R.id.emailLayout);
        passwordLayout = view.findViewById(R.id.passwordLayout);
        btnSignIn = view.findViewById(R.id.signInButton);
        googleButtonView = view.findViewById(R.id.google_login_btn);
        goToSignUp = view.findViewById(R.id.gotoSignup);
    }

    private void setupClickListeners() {
        btnSignIn.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            authPresenter.signInWithEmail(email, password);
        });

        googleButtonView.setOnClickListener(v -> {
            if (googleSignInClient != null) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            } else {
                showError("Google Sign-In not configured");
            }
        });

        goToSignUp.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_signInFragment_to_signUpFragment);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // إرسال الـ account إلى الـ Presenter
                authPresenter.onGoogleSignInResult(account);
            } catch (ApiException e) {
                Log.e(TAG, "Google Sign-In failed: " + e.getStatusCode());
                showError("Google sign in failed");
            }
        }
    }


    @Override
    public void showLoading() {
        if (btnSignIn != null) {
            btnSignIn.setText("Signing in...");
            btnSignIn.setEnabled(false);
        }
    }

    @Override
    public void hideLoading() {
        if (btnSignIn != null) {
            btnSignIn.setText("Sign In");
            btnSignIn.setEnabled(true);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccess(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHomePage() {
        try {
            Intent intent = new Intent(requireActivity(),
                    com.example.food_app_planner.archistartcode.presentation.homepage.view.HomePage.class);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        } catch (Exception e) {
            Log.e(TAG, "Navigation failed: " + e.getMessage());
            showError("Cannot navigate to home page");
        }
    }

    @Override
    public void navigateToSignUp() {
        // Not needed here - handled by XML navigation
    }

    @Override
    public void setEmailError(String error) {
        if (emailLayout != null) {
            emailLayout.setError(error);
        }
    }

    @Override
    public void setPasswordError(String error) {
        if (passwordLayout != null) {
            passwordLayout.setError(error);
        }
    }

    @Override
    public void clearErrors() {
        if (emailLayout != null) {
            emailLayout.setError(null);
        }
        if (passwordLayout != null) {
            passwordLayout.setError(null);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up presenter
        if (authPresenter != null) {
            authPresenter.onDestroy();
        }
    }
}