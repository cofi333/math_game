package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private CircularProgressBar circularProgressBar;

    // Add this variable

    private static long TIMER_DURATION = 30000; // 30 seconds
    private int whatTask=1;
    private int whatLevel=1;
    private CountDownTimer countDownTimer;

    TextView scoreTextView;
    TextView userAnswerEditText;
    TextView taskBefore;
    TextView taskBefore2;
    TextView subtractionExerciseTextView;
    private PopupWindow popupWindow;
    private Button closeButton;
    private View popupView;

    ImageView pauseButton;
    int correctAnswer;
    int lengthOfCorrectAnswer;
    int lengthOfUserAnswer;
    int score;
    float oneStepForOneSecond;
    int totalSeconds;
    public Button b0;
    public Button b1;
    public Button b2;
    public Button b3;
    public Button b4;
    public Button b5;
    public Button b6;
    public Button b7;
    public Button b8;
    public Button b9;
    public Button bDel;

    public void showPopupWindow(int whatTask) {

        LayoutInflater inflater ;
        View popupView;
        if(whatTask==5)
        {

            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popupView = inflater.inflate(R.layout.popup_level_complete, null);


        }
        else if(whatTask==99)
        {

            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popupView = inflater.inflate(R.layout.game_over, null);


        }
        else {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popupView = inflater.inflate(R.layout.popup_layout, null);
        }


        // Access views in the popup layout
//        TextView popupTextView = popupView.findViewById(R.id.popupTextView);
        Button closeButton = popupView.findViewById(R.id.closeButton);


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

        View backgroundView = new View(PlayActivity.this);
        backgroundView.setBackgroundColor(ContextCompat.getColor(PlayActivity.this, android.R.color.black));
        backgroundView.setAlpha(0.5f);

        // Add the background view to the root layout
        ViewGroup rootView = findViewById(android.R.id.content);
        rootView.addView(backgroundView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Dismiss the popup window and remove the background when clicked outside of it

        // Set up the close button or other interactions
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                backgroundView.setAlpha(0f);
            }
        });

        // Show the pop-up window at a specific location
        popupWindow.showAtLocation(findViewById(R.id.activity_play_relay), Gravity.CENTER, 0, 0);

        // Dismiss the pop-up window when clicked outside of it
//        popupView.setOnTouchListener((v, event) -> {
//            popupWindow.dismiss();
//
//            return true;
//        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                restartTimer();
            }
        });
    }



    // Method to handle button click inside the popup
    public void onCloseButtonClick(View view) {
        popupWindow.dismiss();

    }
    private void restartTimer() {
        Log.d(TAG, "restartTimer called");
        TIMER_DURATION = Math.max(TIMER_DURATION, 0);
        initialProgress = (int) ((TIMER_DURATION / 1000) * oneStepForOneSecond);

        Log.d(TAG, "Progress: " + initialProgress);

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        startTimer();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup_layout, null);

        userAnswerEditText = findViewById(R.id.userInput);
        b0 = findViewById(R.id.btn0);
        b1 = findViewById(R.id.btn1);
        b2 = findViewById(R.id.btn2);
        b3 = findViewById(R.id.btn3);
        b4 = findViewById(R.id.btn4);
        b5 = findViewById(R.id.btn5);
        b6 = findViewById(R.id.btn6);
        b7 = findViewById(R.id.btn7);
        b8 = findViewById(R.id.btn8);
        b9 = findViewById(R.id.btn9);
        bDel = findViewById(R.id.btnDel);
        scoreTextView = findViewById(R.id.score);
        taskBefore = findViewById(R.id.taskBefore);
        taskBefore2 = findViewById(R.id.taskBefore2);
        subtractionExerciseTextView = findViewById(R.id.subtractionExerciseTextView);
        pauseButton = findViewById(R.id.pauseImageView);
        circularProgressBar = findViewById(R.id.circularProgressBar);

        generateTaskLevel1();
        startTimer();



        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(PlayActivity.this, PauseActivity.class));
                countDownTimer.cancel();
                showPopupWindow(whatTask);
            }
        });


        for (int i = 0; i <= 9; i++) {
            final int buttonValue = i;
            // Assuming you have buttons with IDs btn0, btn1, ..., btn9
            Button button = findViewById(getResources().getIdentifier("btn" + i, "id", getPackageName()));

            // Set click listener for each button
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userAnswerText = userAnswerEditText.getText().toString();
                    lengthOfUserAnswer = userAnswerText.length();

                    int originalLengthOfUserAnswer = userAnswerEditText.length();
                    userAnswerEditText.append(String.valueOf(buttonValue));

                    if (originalLengthOfUserAnswer < lengthOfCorrectAnswer - 1) {
                        // Continue appending
                    } else {
                        userAnswerText = userAnswerEditText.getText().toString();

                        if (checkAnswer(correctAnswer, Integer.parseInt(userAnswerText))) {
                            score+=50;
                            scoreTextView.setText("Score: " + score);
                            taskBefore2.setTextColor(taskBefore.getCurrentTextColor());
                            taskBefore.setTextColor(Color.parseColor("#00FF00"));

                        } else {
                            taskBefore2.setTextColor(taskBefore.getCurrentTextColor());
                            taskBefore.setTextColor(Color.parseColor("#FF0000"));
                            decrementTimer(5000);


                        }
                        String task1 =String.valueOf(subtractionExerciseTextView.getText());
                        String task2 = String.valueOf(taskBefore.getText());
                        taskBefore2.setText(task2);
                        taskBefore.setText(task1);
                        //neki if (if i=1 pokreni task 1 ako je 2 onda pokreni task 2
                        if(whatTask<5)
                        {
                            generateTaskLevel1();
                            whatTask+=1;

                        }
                        else {

                            countDownTimer.cancel();
                            showPopupWindow(whatTask);
                            whatTask=0;
                        }


                    }

                }
            });

            bDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Add logic for deleting a character from the EditText
                    String currentText = userAnswerEditText.getText().toString();
                    if (!currentText.isEmpty()) {
                        userAnswerEditText.setText(currentText.substring(0, currentText.length() - 1));
                    }
                }
            });
        }

        closeButton = popupView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseButtonClick(v);
            }
        });



    }

    private void generateTaskLevel1() {
        Random random = new Random();
        int operand1 = random.nextInt(9) + 1;
        int operand2 = random.nextInt(9) + 1;
        if(whatLevel==1)
        {

            correctAnswer = operand1 + operand2;
        }
        else if(whatLevel==2)
        {
            correctAnswer = operand1 - operand2;
        }
        lengthOfCorrectAnswer = String.valueOf(correctAnswer).length();



        TextView userAnswerEditText = findViewById(R.id.userInput);
        subtractionExerciseTextView.setText(operand1 + " + " + operand2 + "");

        userAnswerEditText.setText("");
    }

    private boolean checkAnswer(int correctAnswer, int userAnswer) {
        if(correctAnswer == userAnswer) {
            return true;
        }

        return false;
    }

    // Add this variable
    private int initialProgress;
    private void startTimer() {
        Log.d(TAG, "start timer called");
        totalSeconds = (int) (30000 / 1000);
        oneStepForOneSecond = (360.0f / totalSeconds);
        initialProgress = 360;  // Set the initial progress to the full circle

        countDownTimer = new CountDownTimer(TIMER_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TIMER_DURATION = millisUntilFinished;
                Log.d(TAG, "Remaining Time: " + TIMER_DURATION);

                // Calculate progress based on the initial progress and the elapsed time
                int elapsedSeconds = (int) ((TIMER_DURATION / 1000));
                int progress = (int) (initialProgress-(oneStepForOneSecond * elapsedSeconds));
                circularProgressBar.setProgress(progress);
                circularProgressBar.setProgressMax(360);
                circularProgressBar.invalidate();
            }

            @Override
            public void onFinish() {
                // Timer finished, perform actions here
                Toast.makeText(PlayActivity.this, "Timer finished!", Toast.LENGTH_SHORT).show();
               showPopupWindow(99);
            }
        };

        countDownTimer.start();
    }



    private void decrementTimer(long decrementValue) {
        TIMER_DURATION -= decrementValue;
        TIMER_DURATION = Math.max(TIMER_DURATION, 0);

        initialProgress = (int) ((TIMER_DURATION / 1000) * oneStepForOneSecond);


        Log.d(TAG, "Progress: " + initialProgress);
        countDownTimer.cancel();
        startTimer();
    }
}

