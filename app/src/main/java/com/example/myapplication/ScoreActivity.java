package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {
    String apiResponse = "";
    public TextView response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        response = findViewById(R.id.textView3);
        Intent intent = getIntent();

        apiResponse = intent.getStringExtra("apiResponse");
        response.setText(apiResponse);
    }
}
