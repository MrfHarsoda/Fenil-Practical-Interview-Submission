package com.practical.fenilredwhitepractical.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.practical.fenilredwhitepractical.R;
import com.practical.fenilredwhitepractical.pref.AccountProvider;

public class SplashActivity extends AppCompatActivity {
    AccountProvider accountProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        accountProvider = new AccountProvider(this);

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
                if (accountProvider.isLoggedIn()) {
                    startActivity(new Intent(SplashActivity.this, HomePageActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
               finish();
           }
       },3000);
    }
}