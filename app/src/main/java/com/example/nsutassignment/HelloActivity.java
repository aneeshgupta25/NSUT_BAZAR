package com.example.nsutassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelloActivity extends AppCompatActivity {

    Button buttonSignUP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        getSupportActionBar().setTitle("NSUT BAZAR");
        setBindView();

    }

    private void setBindView() {
        buttonSignUP = findViewById(R.id.buttonSIGNUP);
        buttonSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelloActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}