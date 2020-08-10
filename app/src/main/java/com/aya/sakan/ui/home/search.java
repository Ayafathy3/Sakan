package com.aya.sakan.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import com.aya.sakan.R;
import com.google.android.material.tabs.TabLayout;
import com.reginald.editspinner.EditSpinner;

public class search extends AppCompatActivity {
 private Toolbar toolbar;
    private TabLayout tabLayout;
    private EditSpinner almuhafazaEditSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        tabLayout = findViewById(R.id.tab_layout);
        almuhafazaEditSpinner = findViewById(R.id.almuhafaza_name_spinner);


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

    private void setUpTabLayout() {
        // tab layout
        tabLayout.addTab(tabLayout.newTab().setText("للبيع").setIcon(R.drawable.icon1));
        tabLayout.addTab(tabLayout.newTab().setText("للإيجار").setIcon(R.drawable.icon2));
        tabLayout.setInlineLabel(true);


    }}