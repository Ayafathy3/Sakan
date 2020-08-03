package com.aya.sakan.ui.start;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aya.sakan.R;
import com.aya.sakan.ui.home.HomeActivity;
import com.aya.sakan.ui.login.LoginActivity;
import com.aya.sakan.ui.signUp.SignUpActivity;

public class StartActivity extends AppCompatActivity {
    private Button signUp;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        intiViews();
        setListeners();

    }

    private void setListeners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUp();
            }
        });
    }

    private void intiViews() {
        login = findViewById(R.id.login_text);
        signUp = findViewById(R.id.sign_up_button);
    }

    private void goToLogin() {
        startActivity(new Intent(StartActivity.this, LoginActivity.class));
    }

    private void goToSignUp() {
        startActivity(new Intent(StartActivity.this, SignUpActivity.class));
    }
}
