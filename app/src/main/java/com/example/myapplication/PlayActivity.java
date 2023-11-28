package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private CircularProgressBar circularProgressBar;

     // Add this variable

    private static long TIMER_DURATION = 30000; // 30 seconds
    private CountDownTimer countDownTimer;
    TextView scoreTextView;
    TextView userAnswerEditText;
    TextView taskBefore;
    TextView taskBefore2;
    TextView subtractionExerciseTextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


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
                startActivity(new Intent(PlayActivity.this, PauseActivity.class));
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
                        generateTaskLevel1();

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



    }

    private void generateTaskLevel1() {
        Random random = new Random();
        int operand1 = random.nextInt(9) + 1;
        int operand2 = random.nextInt(9) + 1;
        correctAnswer = operand1 + operand2;
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
                startActivity(new Intent(PlayActivity.this, PauseActivity.class));
            }
        };

        countDownTimer.start();
    }

    private void decrementTimer(long decrementValue) {
        // Decrement the timer duration by the specified value
        TIMER_DURATION -= decrementValue;
        // Ensure the timer does not go below 0
        TIMER_DURATION = Math.max(TIMER_DURATION, 0);

        // Calculate the initial progress based on the remaining time
        initialProgress = (int) ((TIMER_DURATION / 1000) * oneStepForOneSecond);

        // Update the progress bar without restarting the timer
//        circularProgressBar.setProgress(initialProgress);
//        circularProgressBar.setProgressMax(360);
//        circularProgressBar.invalidate();

        Log.d(TAG, "Progress: " + initialProgress);
        countDownTimer.cancel();
        // Restart the timer with the updated progress
        startTimer();
    }


//    private void updateTimer() {
//        long minutes = (timeRemainingInMillis / 1000) / 60;
//        long seconds = (timeRemainingInMillis / 1000) % 60;
//
//        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
//        TextView timerTextView = findViewById(R.id.timerTextView);
//        timerTextView.setText(timeFormatted);
//    }

}

//    private void decrementTimer(long decrementValue) {
//        // Decrement the timer duration by the specified value
//        TIMER_DURATION -= decrementValue;
//        // Ensure the timer does not go below 0
//        TIMER_DURATION = Math.max(TIMER_DURATION, 0);
//
//        // Update the progress bar directly
//        int remainingSeconds = (int) (TIMER_DURATION / 1000);
//        int progress = (int) (360 - oneStepForOneSecond * remainingSeconds);
//        circularProgressBar.setProgress(progress);
//        circularProgressBar.setProgressMax(360);
//        circularProgressBar.invalidate();
//
//        Log.d(TAG, "Remaining Time: " + TIMER_DURATION);
//    }
