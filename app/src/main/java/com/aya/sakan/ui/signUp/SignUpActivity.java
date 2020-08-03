package com.aya.sakan.ui.signUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aya.sakan.R;
import com.aya.sakan.ui.home.HomeActivity;
import com.aya.sakan.ui.login.LoginActivity;
import com.aya.sakan.util.LoadingDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;


public class SignUpActivity extends AppCompatActivity implements ISignUpPresenterContract.View {
    private static final int RC_SIGN_IN = 100;
    public static final String TAG = "SignUpActivity";
    private LoginButton loginButtonFB;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    private Button facebookSignUp, googleSignUp, signUp;
    private EditText emailEditText, passwordEditText, confirmPassEditText, rentedNameEditText, rentedPhoneEditText;
    private EditSpinner accountTypeEditSpinner;
    private MaterialCardView rentedCardView, tenantCardView;
    private LinearLayout rentedEditsLinearLayout;
    private ArrayList<String> accountTypeList;
    private String accountType;
    private SignUpPresenterImp signUpPresenterImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initiViews();
        createInstance();
        setUpSpinner();
        setListeners();
    }

    private void createInstance() {

        signUpPresenterImp = new SignUpPresenterImp(this, SignUpActivity.this);

        // Configure Google Sign In
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // Configure Facebook Sign In
        FacebookSdk.sdkInitialize(SignUpActivity.this);
        callbackManager = CallbackManager.Factory.create();
        loginButtonFB.setReadPermissions("email", "public_profile");
    }

    private void initiViews() {
        accountTypeEditSpinner = findViewById(R.id.account_type_spinner);
        emailEditText = findViewById(R.id.email_edit);
        passwordEditText = findViewById(R.id.pass_edit);
        confirmPassEditText = findViewById(R.id.re_pass_edit);
        rentedNameEditText = findViewById(R.id.rented_name_edit);
        rentedPhoneEditText = findViewById(R.id.phone_edit);
        rentedCardView = findViewById(R.id.card_view_rented);
        tenantCardView = findViewById(R.id.card_view_tenant);
        tenantCardView.setChecked(true);
        signUp = findViewById(R.id.button_signUp);
        facebookSignUp = findViewById(R.id.fb);
        googleSignUp = findViewById(R.id.google_sign_in);
        loginButtonFB = findViewById(R.id.login_button_fb);
        rentedEditsLinearLayout = findViewById(R.id.rented_edits);
    }

    private void openHomeActivity() {
        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
        finishAffinity();
    }

    private void setListeners() {

        loginButtonFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                signUpPresenterImp.loginWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                LoadingDialog.hideProgress();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                LoadingDialog.hideProgress();
            }
        });

        googleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog.showProgress(SignUpActivity.this);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        facebookSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonFB.performClick();
                LoadingDialog.showProgress(SignUpActivity.this);
            }
        });

        accountTypeEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                accountType = accountTypeList.get(i).toString();
                Log.i("accountType", accountType);
            }
        });

        rentedCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!rentedCardView.isChecked()) {
                    tenantCardView.setChecked(false);
                    rentedEditsLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    tenantCardView.setChecked(true);
                    rentedEditsLinearLayout.setVisibility(View.GONE);
                }

                //cardView.setChecked(!cardView.isChecked());
                rentedCardView.toggle();
            }
        });

        tenantCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tenantCardView.isChecked()) {
                    rentedCardView.setChecked(false);
                    rentedEditsLinearLayout.setVisibility(View.GONE);
                } else {
                    rentedCardView.setChecked(true);
                    rentedEditsLinearLayout.setVisibility(View.VISIBLE);
                }

                //cardView.setChecked(!cardView.isChecked());
                tenantCardView.toggle();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpAction();
            }
        });
    }

    private void signUpAction() {
        String email = emailEditText.getText().toString();
        String pass = passwordEditText.getText().toString();
        String confirmPass = confirmPassEditText.getText().toString();


        if (tenantCardView.isChecked()) {
            // tenant
            if (email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Please write all data", Toast.LENGTH_SHORT).show();
                return;
            } else {
                int valid = checkValidation(email, pass, confirmPass);
                if (valid == 1) {
                    // go to signUp
                    signUpPresenterImp.signUp(email, pass, null, null, null);
                }
            }
        } else {
            // rented(home)
            String rentedName = rentedNameEditText.getText().toString();
            String phone = rentedPhoneEditText.getText().toString();
            if (email.isEmpty() || rentedName.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()
                    || phone.isEmpty() || accountType.isEmpty()) {
                Toast.makeText(this, "Please write all data", Toast.LENGTH_SHORT).show();
                return;
            } else {
                int valid = checkValidation(email, pass, confirmPass);
                if (valid == 1) {
                    // go to signUp
                    signUpPresenterImp.signUp(email, pass, rentedName, phone, accountType);
                }
            }
        }

    }

    private int checkValidation(String email, String pass, String confirmPass) {
        int valid = 1;
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            emailEditText.setError(getString(R.string.invalid_email));
            emailEditText.setFocusable(true);
            valid = 0;
        } else if (pass.length() < 6) {

            passwordEditText.setError(getString(R.string.pass_error));
            passwordEditText.setFocusable(true);
            valid = 0;
        } else if (!pass.equals(confirmPass)) {

            confirmPassEditText.setError(getString(R.string.confirm_pass_error));
            confirmPassEditText.setFocusable(true);
            valid = 0;
        }
        return valid;
    }

    private void setUpSpinner() {
        accountTypeList = new ArrayList<>();
        accountTypeList.add("المالك، ممثل عن المالك");
        accountTypeList.add("شركة، مكتب عقاري");
        ArrayAdapter<String> accountTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, accountTypeList);
        accountTypeEditSpinner.setAdapter(accountTypeAdapter);
    }

    @Override
    public void goToHome(FirebaseUser user) {
        LoadingDialog.hideProgress();
        if (user != null) {
            openHomeActivity();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                signUpPresenterImp.loginWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                LoadingDialog.hideProgress();
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        } else {
            // Pass the activity result back to the Facebook SDK
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
