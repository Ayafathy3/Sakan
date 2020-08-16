package com.aya.sakan.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aya.sakan.R;
import com.aya.sakan.ui.addPost.AddPostActivity;
import com.aya.sakan.ui.home.HomeActivity;
import com.aya.sakan.util.Data;
import com.google.android.material.tabs.TabLayout;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageButton profile, logout;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initiViews();
        addFragment(new SearchFragment());
        setListeners();
    }

    private void setListeners() {

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, HomeActivity.class));
                finishAffinity();
            }
        });
    }

    private void initiViews() {
        toolbar = findViewById(R.id.tool_bar);
        profile = findViewById(R.id.profile);
        logout = findViewById(R.id.logout);
        title = findViewById(R.id.title);

        toolbar.setNestedScrollingEnabled(false);
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();
    }
}