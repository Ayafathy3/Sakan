package com.aya.sakan.ui.Splash;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.aya.sakan.R;
import com.aya.sakan.ui.home.HomeActivity;
import com.aya.sakan.ui.login.LoginActivity;
import com.aya.sakan.ui.start.StartActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Get_hash_key();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    checkIfUserIsLogged();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void Get_hash_key() {
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.aya.sakan", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash_key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }
    private void checkIfUserIsLogged() {

        // firebase with email and pass
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // signed in with google
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(SplashActivity.this);

        if (currentUser == null && account == null) {
            goToStartActivity();
        } else {
            openHomeActivity();
        }
    }

    private void goToStartActivity() {
        startActivity(new Intent(SplashActivity.this, StartActivity.class));
        finish();
    }

    private void openHomeActivity() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finishAffinity();
    }
}
