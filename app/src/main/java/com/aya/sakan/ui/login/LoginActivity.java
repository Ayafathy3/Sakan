package com.aya.sakan.ui.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aya.sakan.R;
import com.aya.sakan.ui.home.HomeActivity;
import com.aya.sakan.ui.signUp.SignUpActivity;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements ILoginPresenterContract.View {
    private static final int RC_SIGN_IN = 100;
    public static final String TAG = "LoginActivity";
    private TextInputEditText emailEdit, passEdit;
    private Button loginButton, facebookButton, googleButton;
    private LoginButton loginButtonFB;
    private TextView recoverPass, signUp;
    private LoginPresenterImp loginPresenterImp;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initiViews();
        createInstance();
        setListeners();


    }

    private void createInstance() {
        loginPresenterImp = new LoginPresenterImp(this, LoginActivity.this);

        // Configure Google Sign In
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // Configure Facebook Sign In
        FacebookSdk.sdkInitialize(LoginActivity.this);
        callbackManager = CallbackManager.Factory.create();
        loginButtonFB.setReadPermissions("email", "public_profile");

    }

    private void setListeners() {
        loginButtonFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                loginPresenterImp.loginWithFacebook(loginResult.getAccessToken());
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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAction();
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog.showProgress(LoginActivity.this);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        recoverPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgetPassAction();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUp();
            }
        });

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonFB.performClick();
                LoadingDialog.showProgress(LoginActivity.this);
            }
        });
    }

    private void initiViews() {
        emailEdit = findViewById(R.id.email_edit);
        passEdit = findViewById(R.id.pass_edit);
        loginButton = findViewById(R.id.button_login);
        facebookButton = findViewById(R.id.fb);
        googleButton = findViewById(R.id.google_sign_in);
        recoverPass = findViewById(R.id.pass_forget);
        signUp = findViewById(R.id.sign_up_text);
        loginButtonFB = findViewById(R.id.login_button_fb);
    }

    private void goToSignUp() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    private void loginAction() {
        String email = emailEdit.getText().toString();
        String pass = passEdit.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            emailEdit.setError(getString(R.string.invalid_email));
            emailEdit.setFocusable(true);

        } else if (pass.length() < 6) {

            passEdit.setError(getString(R.string.pass_error));
            passEdit.setFocusable(true);

        } else {
            loginPresenterImp.loginAction(email, pass);
        }
    }

    private void forgetPassAction() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(R.string.recover_pass);

        LinearLayout linearLayout = new LinearLayout(LoginActivity.this);

        final EditText editText = new EditText(this);
        editText.setHint(R.string.email);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(editText);
        linearLayout.setPadding(10, 10, 10, 10);

        builder.setView(linearLayout);

        builder.setPositiveButton(R.string.recover, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String email = editText.getText().toString().trim();
                loginPresenterImp.recoverPass(email);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public void goToHome(FirebaseUser user) {
        LoadingDialog.hideProgress();
        if (user != null) {
            openHomeActivity();
        }
    }

    @Override
    public void resultPassRecover(String result) {
        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
    }

    private void openHomeActivity() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finishAffinity();
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
                loginPresenterImp.loginWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                LoadingDialog.hideProgress();
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        } else {
            // Pass the activity result back to the Facebook SDK
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
