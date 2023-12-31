package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private CircularProgressBar circularProgressBar;

    private static long TIMER_DURATION = 30000; // 30 seconds
    private CountDownTimer countDownTimer;
    MediaPlayer mp;

    TextView scoreTextView;
    TextView userAnswerEditText;
    TextView taskBefore;
    TextView taskBefore2;
    TextView subtractionExerciseTextView;
    TextView showLevel;
    private PopupWindow popupWindow;
    private Button closeButton;
    private Button menuButton;
    private View popupView;
    int currentLevelScore;
    ImageView pauseButton;
    int correctAnswer;
    int whatLevel = 1;
    int whatTask = 1;
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
    RequestQueue requestQueue;

    @SuppressLint("InflateParams")
    public void showPopupWindow(int whatTask2) {

        pauseButton.setClickable(false);
        bDel.setClickable(false);
        b9.setClickable(false);
        b8.setClickable(false);
        b0.setClickable(false);
        b7.setClickable(false);




        View backgroundView = new View(PlayActivity.this);
        backgroundView.setBackgroundColor(ContextCompat.getColor(PlayActivity.this, android.R.color.black));
        backgroundView.setAlpha(0.5f);

        // Add the background view to the root layout
        ViewGroup rootView = findViewById(android.R.id.content);
        rootView.addView(backgroundView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LayoutInflater inflater;
        View popupView;
        if(whatTask2==5)
        {

            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popupView = inflater.inflate(R.layout.popup_level_complete, null);

            TextView scoreTextView = popupView.findViewById(R.id.currentScoreLevelCompleted);
            scoreTextView.setText("Current score: \n" + score);
            Button nextLevelBtn = popupView.findViewById(R.id.nextLevelBtn);
            Button submitScoreBtn = popupView.findViewById(R.id.submitScoreBtn);
            Button exitGameBtn = popupView.findViewById(R.id.exitGameBtn);
            submitScoreBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    showAlertDialog();
                }
            });
            nextLevelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskBefore.setText("");
                    taskBefore2.setText("");
                    whatLevel++;
                    currentLevelScore = 0;
                    showLevel.setText("Level: \n" + whatLevel + "/5");
                    generateTaskLevel1(whatLevel);
                    popupWindow.dismiss();
                    backgroundView.setAlpha(0f);
                    restartTimer(whatTask2);
                }
            });

            exitGameBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toMenu();
                }
            });
        }
        else if(whatTask2==10)
        {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popupView = inflater.inflate(R.layout.game_over, null);
            TextView finalScore = popupView.findViewById(R.id.finalScoreTxtView);
            finalScore.setText("Your score:\n" + score);
            Button tryAgainBtn = popupView.findViewById(R.id.tryAgainBtn);
            Button submitScore = popupView.findViewById(R.id.submitScoreBtn);
            whatTask = 1;
            taskBefore.setText("");
            taskBefore2.setText("");
            tryAgainBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "tryAgainBtn onClick");

                    whatLevel = 1;
                    whatTask = 1;
                    generateTaskLevel1(whatLevel);
                    score = 0;
                    scoreTextView.setText("Score: " + score);
                    showLevel.setText("Level: \n" + whatLevel + "/5");

                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }


                    backgroundView.setAlpha(0f);
                    restartTimer(10);
                }
            });

            submitScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialog();
                }
            });



        }

        else if (whatTask2 == 99) {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popupView = inflater.inflate(R.layout.popup_level_failed, null);
            Button restartLevelBtn = popupView.findViewById(R.id.restartLevelBtn);
            Button endGameBtn = popupView.findViewById(R.id.endGameBtn);

            endGameBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toMenu();
                }
            });

            restartLevelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        score = score - (currentLevelScore * 50);
                        scoreTextView.setText("Score: " + score);
                        popupWindow.dismiss();
                        backgroundView.setAlpha(0f);
                        whatTask = 1;
                        taskBefore.setText("");
                        taskBefore2.setText("");
                        currentLevelScore = 0;
                    }
                }
            });
        }


        else {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popupView = inflater.inflate(R.layout.popup_layout, null);
            closeButton = popupView.findViewById(R.id.closeButton);

            Button soundOnButton = popupView.findViewById(R.id.soundOn_button);
            Button soundOffButton = popupView.findViewById(R.id.soundOff_button);
            menuButton = popupView.findViewById(R.id.menuBtn);
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toMenu();
                }
            });

            soundOnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mp.isPlaying()) {
                        Toast.makeText(PlayActivity.this,"The sound is already on.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        mp.start();
                    }
                }
            });

            soundOffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!mp.isPlaying()) {
                        Toast.makeText(PlayActivity.this,"The sound is already off.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        mp.pause();
                    }
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
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                backgroundView.setAlpha(0f);
            }
        });

        // Show the pop-up window at a specific location
        popupWindow.showAtLocation(findViewById(R.id.activity_play_relay), Gravity.CENTER, 0, 0);

      popupView.setOnTouchListener((v, event) -> {
            return true;
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                restartTimer(whatTask2);
                pauseButton.setClickable(true);
                bDel.setClickable(true);
                b9.setClickable(true);
                b8.setClickable(true);
                b0.setClickable(true);
                b7.setClickable(true);

            }
        });
    }




    // Method to handle button click inside the popup
    public void onCloseButtonClick(View view) {
        popupWindow.dismiss();

    }



    public void toMenu()
    {

        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }

        Intent intent = new Intent(PlayActivity.this, MainActivity.class);

        startActivity(intent);

        TIMER_DURATION = 30000;

        // Calculate initial progress based on the reset TIMER_DURATION
        initialProgress = (int) ((TIMER_DURATION / 1000) * oneStepForOneSecond);


        // Cancel the existing timer, if any
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    private void restartTimer(int whatTask2) {
        if(whatTask2!=5 && whatTask2!=99 && whatTask2!=10)
        {
            Log.d(TAG, "restartTimer called");
            TIMER_DURATION = Math.max(TIMER_DURATION, 0);
            initialProgress = (int) ((TIMER_DURATION / 1000) * oneStepForOneSecond);

            Log.d(TAG, "what task2: " + whatTask2);


            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            startTimer();
        }
        else
        {
            Log.d(TAG, "restartTimer called");

            // Reset TIMER_DURATION to the initial value (e.g., 30 seconds)
            TIMER_DURATION = 30000;
            Log.d(TAG, "what task2: " + whatTask2);
            // Calculate initial progress based on the reset TIMER_DURATION
            initialProgress = (int) ((TIMER_DURATION / 1000) * oneStepForOneSecond);

            Log.d(TAG, "Progress: " + initialProgress);

            // Cancel the existing timer, if any
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            // Start the timer again
            startTimer();
        }

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
        showLevel = findViewById(R.id.showLevel);
        if (mp == null) {
            // If not, create a new instance
            mp = MediaPlayer.create(this, R.raw.game_music);
            mp.start();
            mp.setLooping(true);
        }
        generateTaskLevel1(whatLevel);
        startTimer();



        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(PlayActivity.this, PauseActivity.class));
                countDownTimer.cancel();
                showPopupWindow(0);
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
                            currentLevelScore++;
                            scoreTextView.setText("Score: " + score);
                            taskBefore2.setTextColor(taskBefore.getCurrentTextColor());
                            taskBefore.setTextColor(Color.parseColor("#00FF00"));

                        } else {
                            taskBefore2.setTextColor(taskBefore.getCurrentTextColor());
                            taskBefore.setTextColor(Color.parseColor("#FF0000"));
                            decrementTimer(2000);


                        }
                        String task1 =String.valueOf(subtractionExerciseTextView.getText());
                        String task2 = String.valueOf(taskBefore.getText());
                        taskBefore2.setText(task2);
                        taskBefore.setText(task1);
                        //neki if (if i=1 pokreni task 1 ako je 2 onda pokreni task 2
                        if(whatTask<5)
                        {
                            generateTaskLevel1(whatLevel);
                            whatTask+=1;

                        }
                        else if(whatLevel == 5) {
//                            countDownTimer.cancel();

                            showPopupWindow(10);
                            countDownTimer.cancel();

                        }
                        else {
                            countDownTimer.cancel();
                            showPopupWindow(whatTask);
                            whatTask=1;
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

    /** @noinspection deprecation*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        toMenu();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Release MediaPlayer resources
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }

    private void generateTaskLevel1(int level) {
        Random random = new Random();

        if(level==1)
        {
            String[] operations = {"+", "-"};
            String selectedOperation = operations[random.nextInt(operations.length)];

            int operand1 = random.nextInt(9) + 1;
            int operand2 = random.nextInt(operand1) + 1;

            if (selectedOperation.equals("+")) {

                correctAnswer = operand1 + operand2;
                subtractionExerciseTextView.setText(operand1 + " + " + operand2 + "");
            } else {

                correctAnswer = operand1 - operand2;
                subtractionExerciseTextView.setText(operand1 + " - " + operand2 + "");

            }

        }
        else if (level == 2) {
            String[] operations = {"*", "/"};
            String selectedOperation = operations[random.nextInt(operations.length)];

            // Generate two-digit operands
            int operand1 = random.nextInt(90) + 10; // Two-digit number
            int operand2 = random.nextInt(90) + 10; // Two-digit number

            // Ensure operand1 is divisible by operand2 when using division operation
            if (selectedOperation.equals("/")) {
                while (operand1 % operand2 != 0) {
                     operand1 = random.nextInt(90) + 10; // Two-digit number
                     operand2 = random.nextInt(90) + 10;
                }

            }

            if (selectedOperation.equals("*")) {
                correctAnswer = operand1 * operand2;
                subtractionExerciseTextView.setText(operand1 + " * " + operand2 + "");
            } else {
                correctAnswer = operand1 / operand2;
                subtractionExerciseTextView.setText(operand1 + " / " + operand2 + "");
            }
        }




        else if (level == 3) {
            String[] operations = {"+","-","*", "/"};
            String selectedOperation = operations[random.nextInt(operations.length)];

            int operand1 = random.nextInt(11) + 10;
            int operand2 = random.nextInt(operand1 - 9) + 10;


            if (selectedOperation.equals("+")) {
                correctAnswer = operand1 + operand2;
                subtractionExerciseTextView.setText(operand1 + " + " + operand2 + "");
            }

            if (selectedOperation.equals("-")) {
                correctAnswer = operand1 - operand2;
                subtractionExerciseTextView.setText(operand1 + " - " + operand2 + "");
            }

            if (selectedOperation.equals("*")) {
                correctAnswer = operand1 * operand2;
                subtractionExerciseTextView.setText(operand1 + " * " + operand2 + "");
            }

            if(selectedOperation.equals("/")){
                while (operand1 % operand2 != 0) {
                    operand1 = random.nextInt(90) + 10; // Two-digit number
                    operand2 = random.nextInt(90) + 10;
                }
                correctAnswer = operand1 / operand2;
                subtractionExerciseTextView.setText(operand1 + " / " + operand2 + "");
            }



        }

        else if (level == 4) {
            int operand1 = random.nextInt(900) + 100; // Generates a random three-digit number
            int operand2 = random.nextInt(operand1 - 100) + 100;
            String[] operations = {"+", "-"};
            String selectedOperation = operations[random.nextInt(operations.length)];

            if (selectedOperation.equals("+")) {
                correctAnswer = operand1 + operand2;
                subtractionExerciseTextView.setText(operand1 + " + " + operand2);
            }

            if (selectedOperation.equals("-")) {
                correctAnswer = operand1 - operand2;
                subtractionExerciseTextView.setText(operand1 + " - " + operand2);
            }
        }

        else if (level == 5) {
            String[] operations = {"*", "/"};
            String selectedOperation = operations[random.nextInt(operations.length)];
            int operand1 = random.nextInt(900) + 100;
            int operand2 = random.nextInt(900) + 100;

            if (selectedOperation.equals("/")) {
                while (operand1 % operand2 != 0) {
                    operand1 = random.nextInt(900) + 100;
                    operand2 = random.nextInt(900) + 100;
                }
            }

            if (selectedOperation.equals("*")) {
                correctAnswer = operand1 * operand2;
                subtractionExerciseTextView.setText(operand1 + " * " + operand2 + "");
            } else {
                correctAnswer = operand1 / operand2;
                subtractionExerciseTextView.setText(operand1 + " / " + operand2 + "");
            }
        }

        lengthOfCorrectAnswer = String.valueOf(correctAnswer).length();



        TextView userAnswerEditText = findViewById(R.id.userInput);


        userAnswerEditText.setText("");
    }

    private boolean checkAnswer(int correctAnswer, int userAnswer) {
        if(correctAnswer == userAnswer) {
            addExtraSecondToTimer();
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

    private void addExtraSecondToTimer() {
        // Cancel the existing timer
        countDownTimer.cancel();

        // Add 1 second to the timer duration
        TIMER_DURATION += 1000;

        // Start a new timer with the updated duration
        startTimer();
    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
        builder.setTitle("Save your highscore");
        builder.setMessage("Your score: " + score);
        final TextView scoreText = new TextView(PlayActivity.this);
        scoreText.setText(String.valueOf(score));
        final EditText username = new EditText(PlayActivity.this);
        username.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(username);
        builder.setPositiveButton("Submit score", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(PlayActivity.this, "Success", Toast.LENGTH_LONG).show();
                String url = "https://mathgameapi.000webhostapp.com/api/scores/";
                JSONObject jsonParams = new JSONObject();
                try {
                    jsonParams.put("username", username.getText().toString());
                    jsonParams.put("score", scoreText.getText().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        response -> Toast.makeText(PlayActivity.this, "Your score has been recorded", Toast.LENGTH_LONG).show(),
                        error -> Toast.makeText(PlayActivity.this, "Please enter your username", Toast.LENGTH_LONG).show()) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        // Convert the JSON object to byte array
                        return jsonParams.toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                };

                requestQueue = Volley.newRequestQueue(PlayActivity.this);
                requestQueue.add(stringRequest);

                toMenu();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}

