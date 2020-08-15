package com.aya.sakan.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.aya.sakan.Prefs.PreferencesHelperImp;
import com.aya.sakan.R;
import com.aya.sakan.ui.addPost.AddPostActivity;
import com.aya.sakan.ui.home.adapters.AdapterFrag;
import com.aya.sakan.ui.search.SearchActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class HomeActivity extends AppCompatActivity {
    private FloatingActionButton addPost;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ImageButton profile, search, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initiViews();

        setUpTabLayout();
        setListeners();
    }

    private void setListeners() {
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
    }


    private void initiViews() {
        toolbar = findViewById(R.id.tool_bar);
        addPost = findViewById(R.id.add_post_btn);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        profile = findViewById(R.id.profile);
        search = findViewById(R.id.search);
        logout = findViewById(R.id.logout);

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
