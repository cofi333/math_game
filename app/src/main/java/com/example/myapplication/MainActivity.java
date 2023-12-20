package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;


/** @noinspection ALL*/
public class MainActivity extends AppCompatActivity {

    public Button playButton;
    public Button helpButton;
    public Button highScoresButton;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.play_button);
        helpButton = findViewById(R.id.help_button);
        highScoresButton = findViewById(R.id.high_scores_button);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }
        });

        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetScores().execute();
            }
        });
    }

    private class  GetScores extends AsyncTask<Void, Void, Void>{
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(MainActivity.this, "JSON podaci se downloaduju", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            com.example.myapplication.HttpHandler sh = new com.example.myapplication.HttpHandler();
            String url="https://mathgameapi.000webhostapp.com/api/scores/";
            String jsonStr = sh.makeServiceCall(url);
            Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
            intent.putExtra("jsonResponse", jsonStr);
            startActivity(intent);
            return null;
        }
    }

    private void openActivity() {
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        startActivity(intent);
    }
}



