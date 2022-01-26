package com.example.nsutassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.PrivilegedAction;

public class SignUpActivity extends AppCompatActivity {

    EditText editTextSignUpMail, editTextSignUpPassword;
    Button buttonLoginSignUp;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setUpSharedPreferences();
        setBindView();
    }

    private void setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences(PreferenceConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
    }

    private void setBindView() {
        editTextSignUpMail = findViewById(R.id.editTextSignUpMail);
        editTextSignUpPassword = findViewById(R.id.editTextSignUpPassword);
        buttonLoginSignUp = findViewById(R.id.buttonLoginSignUp);
        buttonLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailID = editTextSignUpMail.getText().toString();
                String password = editTextSignUpPassword.getText().toString();
                if(!TextUtils.isEmpty(mailID) && !TextUtils.isEmpty(password)){
                    if(mailID.equals("test@admin.com") && password.equals("12345678")){

                        savaLoginStatus();

                        Intent intent = new Intent(SignUpActivity.this, ListActivity.class);
                        startActivity(intent);
                    }else{
                          Toast.makeText(SignUpActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignUpActivity.this, "Mail ID or Password can't be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void savaLoginStatus() {
        editor = sharedPreferences.edit();
        editor.putBoolean(PreferenceConstant.IS_LOGGED_IN, true);
        editor.apply();
    }
}