package com.aya.sakan.ui.signUp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.aya.sakan.Prefs.PreferencesHelperImp;
import com.aya.sakan.util.LoadingDialog;
import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpPresenterImp implements ISignUpPresenterContract.Presenter {

    private ISignUpPresenterContract.View mView;
    private FirebaseAuth mAuth;
    private static final String TAG = "SignUpPresenterImp";
    private Context context;
    FirebaseFirestore firebaseFirestore;

    SignUpPresenterImp(ISignUpPresenterContract.View mView, Context context) {
        this.mView = mView;
        mAuth = FirebaseAuth.getInstance();
        this.context = context;
    }

    @Override
    public void loginWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mView.goToHome(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i(TAG, "signInWithCredential:failure " + task.getException());
                            mView.goToHome(null);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        LoadingDialog.hideProgress();
                        Log.i(TAG, "signInWithCredential:failure  " + e.getMessage());
                    }
                });
    }

    @Override
    public void loginWithFacebook(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mView.goToHome(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            mView.goToHome(null);
                        }
                    }
                });
    }

    @Override
    public void signUp(String mail, String pass, final String rentedName, final String phone, final String accountType) {
        LoadingDialog.showProgress(context);

        if (rentedName == null && phone == null && accountType == null) {
            // tenant
            PreferencesHelperImp.getInstance().setAccountType("tenant");

            mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signUpWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        mView.goToHome(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signUpWithEmail:failure", task.getException());
                        mView.goToHome(null);
                    }
                }
            });
        } else {
            // rented(home)
            PreferencesHelperImp.getInstance().setAccountType("rented");

            mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signUpWithEmail:success");
                        setUpUserData(rentedName, phone, accountType);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signUpWithEmail:failure", task.getException());
                        mView.goToHome(null);
                    }
                }
            });
        }

    }

    private void setUpUserData(String rentedName, String phone, String accountType) {

        String user_id = mAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", rentedName);
        userMap.put("phone", phone);
        userMap.put("account_type", accountType);


        firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Log.d(TAG, "signUpWithFireStore:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    mView.goToHome(user);
                } else {
                    String error = task.getException().getMessage();
                    Log.w(TAG, "signUpWithFireStore:failure " + error);
                    mView.goToHome(null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "signUpWithFireStore:failure " + e.getMessage());
                mView.goToHome(null);
            }
        });
    }


}