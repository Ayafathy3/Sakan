package com.aya.sakan.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.aya.sakan.Prefs.PreferencesHelper;
import com.aya.sakan.Prefs.PreferencesHelperImp;
import com.aya.sakan.R;
import com.aya.sakan.ui.addPost.AddPostActivity;
import com.aya.sakan.ui.home.adapters.AdapterFrag;
import com.aya.sakan.ui.login.LoginActivity;
import com.aya.sakan.ui.postDetails.PostDetailsActivity;
import com.aya.sakan.ui.profile.ProfileActivity;
import com.aya.sakan.ui.search.SearchActivity;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;

import java.util.Locale;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private FloatingActionButton addPost;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ImageButton profile, search, logout;
    private TextView languageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initiViews();
        setUpTabLayout();
        setListeners();
    }

    private void setUpLanguages(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void setListeners() {
        languageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String language = PreferencesHelperImp.getInstance().getLanguagePref();
                if (language.equals("en")) {
                    PreferencesHelperImp.getInstance().setLanguagePref("ar");
                    languageTextView.setText("ar");
                    setUpLanguages("ar");
                } else {
                    PreferencesHelperImp.getInstance().setLanguagePref("en");
                    languageTextView.setText("en");
                    setUpLanguages("en");
                }
            }
        });

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AddPostActivity.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutAction();
            }
        });
    }

    private void logoutAction() {
        new AlertDialog.Builder(
                HomeActivity.this)
                .setTitle(R.string.confirm_logout)
                .setMessage(R.string.confirm_logout_msg)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // signed in with google
                                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(HomeActivity.this);

                                // signed in with fb
                                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

                                if (isLoggedIn) {
                                    facebookSignOut();
                                }

                                if (account != null) {
                                    googleSignOut();
                                }

                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                finishAffinity();
                            }
                        })
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
    }

    private void googleSignOut() {
        Log.i(TAG, "logout google");
        GoogleSignInClient mGoogleSignInClient;
        GoogleSignInOptions googleSignInOptions;

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void facebookSignOut() {
        Log.i(TAG, "logout fb");
        LoginManager.getInstance().logOut();
    }

    private void initiViews() {
        toolbar = findViewById(R.id.tool_bar);
        addPost = findViewById(R.id.add_post_btn);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        profile = findViewById(R.id.profile);
        search = findViewById(R.id.search);
        logout = findViewById(R.id.logout);
        languageTextView = findViewById(R.id.language);

        String language = PreferencesHelperImp.getInstance().getLanguagePref();
        languageTextView.setText(language);

        String accountType = PreferencesHelperImp.getInstance().getAccountType();
        if (accountType != null) {
            if (accountType.equals("tenant")) {
                addPost.setVisibility(View.GONE);
            } else if (accountType.equals("rented")) {
                addPost.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setUpTabLayout() {
        // tab layout
        tabLayout.addTab(tabLayout.newTab().setText("للبيع").setIcon(R.drawable.icon1));
        tabLayout.addTab(tabLayout.newTab().setText("للإيجار").setIcon(R.drawable.icon2));
        tabLayout.setInlineLabel(true);

        viewPager.setAdapter(new AdapterFrag(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

}
