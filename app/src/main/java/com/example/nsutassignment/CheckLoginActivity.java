package com.example.nsutassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckLoginActivity extends AppCompatActivity {

    EditText editTextMail;
    EditText editTextPassword;
    Button buttonLogin;
    TextView textViewSignUp, textViewDHA;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("WELCOME");
        setupSharedPreferences();
        setBindView();
    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PreferenceConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
    }

    private void setBindView() {
        editTextMail = findViewById(R.id.editTextMail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        textViewDHA = findViewById(R.id.textViewDHA);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailID = editTextMail.getText().toString();
                String password = editTextPassword.getText().toString();
                if(!TextUtils.isEmpty(mailID) && !TextUtils.isEmpty(password)){
                    Toast.makeText(CheckLoginActivity.this, "Looks like you don't have an account. \n SIGN UP now!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CheckLoginActivity.this, "MAIL ID or PASSWORD can't be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(
                        HapticFeedbackConstants.VIRTUAL_KEY,
                        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
                );
                textViewSignUp.setHapticFeedbackEnabled(true);
                Intent intent = new Intent(CheckLoginActivity.this, HelloActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveLoginStatus() {
        editor = sharedPreferences.edit();
        editor.putBoolean(PreferenceConstant.IS_LOGGED_IN, true);
        editor.apply();
    }
}