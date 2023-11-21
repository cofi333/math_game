package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    int correctAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        generateTask();

        EditText userAnswerEditText = findViewById(R.id.userAnswerEditText);
        userAnswerEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    checkAnswer();
                    return true;
                }
                return false;
            }


        });

    }

    private void generateTask() {
        Random random = new Random();
        int operand1 = random.nextInt(100 - 0 + 1) + 0;
        int operand2 = random.nextInt(100 - 0 + 1) + 0;
        correctAnswer = operand1 - operand2;

        TextView subtractionExerciseTextView = findViewById(R.id.subtractionExerciseTextView);
        EditText userAnswerEditText = findViewById(R.id.userAnswerEditText);

        subtractionExerciseTextView.setText(operand1 + " - " + operand2 + "");

       userAnswerEditText.setText("");
    }

    private void checkAnswer() {
        EditText userAnswerEditText = findViewById(R.id.userAnswerEditText);
        String userInput = userAnswerEditText.getText().toString();

        if (!userInput.isEmpty()) {
            int userAnswer = Integer.parseInt(userInput);

            if (userAnswer == correctAnswer) {
                Log.d(TAG, "Correct");
            } else {
                Log.d(TAG, "Incorrect");
            }
            generateTask();
        }
    }




}
