package com.example.nsutassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;

public class TestActivity extends AppCompatActivity {

    TextView textView, textViewCheckout;
    LottieAnimationView lottieAnimationView;
    MaterialButton materialButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSupportActionBar().hide();
        setupSharedPreferences();
        setBindView();
    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PreferenceConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
    }

    private void setBindView() {
        textView = findViewById(R.id.textViewHeading);
        textViewCheckout = findViewById(R.id.textViewCheckout);
        lottieAnimationView = findViewById(R.id.listAnimation);
        materialButton = findViewById(R.id.materialButton);

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkLoginStatus()){
                    Intent intent = new Intent(TestActivity.this, ListActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(TestActivity.this, CheckLoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        textViewCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(
                        HapticFeedbackConstants.VIRTUAL_KEY,
                        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
                );
                textViewCheckout.setHapticFeedbackEnabled(true);

                View checkout_view = LayoutInflater.from(TestActivity.this).inflate(R.layout.app_info_layout, null);

                AlertDialog app_info_dialog = new AlertDialog.Builder(TestActivity.this)
                        .setView(checkout_view)
                        .setCancelable(true)
                        .create();
                app_info_dialog.show();
            }
        });
    }

    private boolean checkLoginStatus() {
        boolean is_logged_in = sharedPreferences.getBoolean(PreferenceConstant.IS_LOGGED_IN, false);
        if(is_logged_in){
            return true;
        }else{
            return false;
        }
    }
}