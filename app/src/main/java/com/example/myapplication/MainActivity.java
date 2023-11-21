package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    public Button playButton;
    public Button helpButton;
    public Button highScoresButton;

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
    }

    private void openActivity() {
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        startActivity(intent);
    }


}

