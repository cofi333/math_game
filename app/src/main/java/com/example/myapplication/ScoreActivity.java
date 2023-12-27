package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {
    String apiResponse = "";
    private ListView listView;
    private ArrayList<String> scoreDataList;
    private TextView tv;

    final private String TAG = ScoreActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Intent intent = getIntent();
        apiResponse = intent.getStringExtra("jsonResponse");


        listView = findViewById(R.id.listView1);
        scoreDataList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(apiResponse);
            JSONArray list = jsonObject.getJSONArray("data");
            for (int i = 0; i < list.length(); i++) {
                JSONObject data = list.getJSONObject(i);
                String username = data.getString("username");
                String score = data.getString("score");
                scoreDataList.add("Username: " + username + "\nScore: " + score + "\n");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.score_textview, scoreDataList);
            listView.setAdapter(adapter);
        } catch (JSONException je) {
            je.printStackTrace();
        }


    }
}
