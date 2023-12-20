package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ScoreActivity extends AppCompatActivity {
    String apiResponse = "";
    public TextView response;
    final private String TAG = ScoreActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        response = findViewById(R.id.textView3);
        Intent intent = getIntent();

        apiResponse = intent.getStringExtra("jsonResponse");
        try {
            JSONObject jsonObject = new JSONObject(apiResponse);
            Log.e(TAG, String.valueOf(jsonObject));
            response.setText(jsonObject.toString(4));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
