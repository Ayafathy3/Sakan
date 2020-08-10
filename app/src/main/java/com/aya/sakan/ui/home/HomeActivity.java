package com.aya.sakan.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.aya.sakan.R;
import com.aya.sakan.ui.home.classes.AdapterFrag;
import com.aya.sakan.ui.login.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomeActivity extends AppCompatActivity {
    private FloatingActionButton addPost;
    private FirebaseFirestore firebaseFirestore;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        initiViews();
        setUpToolBar();
        setUpTabLayout();
    }

    private void setUpToolBar() {
        //this is the code of Tool_bar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void initiViews() {
        addPost = findViewById(R.id.add_post_btn);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

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

    private void openLoginActivity() {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }
}
