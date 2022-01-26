package com.example.nsutassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


public class SplashActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        setupSharedPreferences();
        checkLoginStatus();
    }

    private void checkLoginStatus() {
        boolean is_logged_in = sharedPreferences.getBoolean(PreferenceConstant.IS_LOGGED_IN, false);
        if(is_logged_in){
            Intent intent = new Intent(SplashActivity.this, ListActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(SplashActivity.this, CheckLoginActivity.class);
            startActivity(intent);
        }
    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PreferenceConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
    }
}