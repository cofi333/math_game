package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


/** @noinspection ALL*/
public class MainActivity extends AppCompatActivity {

    public Button playButton;

    private PopupWindow popupWindow;
    private View popupView;

    public Button helpButton;
    private ImageView closeButton;

    private  Button backButton;
    public Button highScoresButton;
    public ImageView infoButton;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.play_button);
        helpButton = findViewById(R.id.help_button);
        highScoresButton = findViewById(R.id.high_scores_button);
        infoButton = findViewById(R.id.info_button);

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow("help");
            }
        });


        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow("info");
            }
        });


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }
        });


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


    public void showPopupWindow(String whatExecute) {
        infoButton.setClickable(false);



        View backgroundView = new View(MainActivity.this);
        backgroundView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
        backgroundView.setAlpha(0.5f);

        // Add the background view to the root layout
        ViewGroup rootView = findViewById(android.R.id.content);
        rootView.addView(backgroundView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LayoutInflater inflater;
        View popupView = null;

        if(whatExecute=="help")
        {


            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popupView = inflater.inflate(R.layout.help, null);
            backButton = popupView.findViewById(R.id.goBack);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    backgroundView.setAlpha(0f);
                }
            });

        }

        else if (whatExecute=="info")
        {

            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popupView = inflater.inflate(R.layout.info, null);
            closeButton = popupView.findViewById(R.id.closeButton);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    backgroundView.setAlpha(0f);
                }
            });
        }




        // Create the PopupWindow
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        int popupWidthInDp = 300; // Adjust this value as needed
        int popupHeightInDp = 500; // Adjust this value as needed

        // Convert dp to pixels
        float density = getResources().getDisplayMetrics().density;
        int popupWidthInPixels = (int) (popupWidthInDp * density);
        int popupHeightInPixels = (int) (popupHeightInDp * density);

        // Set width and height in pixels
        popupWindow.setWidth(popupWidthInPixels);
        popupWindow.setHeight(popupHeightInPixels);



        // Dismiss the popup window and remove the background when clicked outside of it

        // Set up the close button or other interactions


        // Show the pop-up window at a specific location
        popupWindow.showAtLocation(findViewById(R.id.mainLayout), Gravity.CENTER, 0, 0);
        popupView.setOnTouchListener((v, event) -> {


            return true;
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                infoButton.setClickable(true);

            }
        });
    }
}



