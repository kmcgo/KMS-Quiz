package com.example.kmsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartingScreenActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String EXTRA_CATEGORY_ID = "extraCategoryID";
    public static final String EXTRA_CATEGORY_NAME = "extraCategoryName";
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private TextView textViewHighscore;
    private Spinner spinnerCategory;
    private Spinner spinnerDifficulty;

    private int highscore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);//Sets starting layout based on associated xml file


        Button btnStart = findViewById(R.id.button_start_quiz);//Create object for button thats bound to button on the start activity page

        btnStart.setOnClickListener(new View.OnClickListener() {//Create event listener that listens for button click and runs the startQuiz method, which starts the quiz activity

            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

    }
    private void startQuiz() {
        //Create intent object which is like an operation that will be performed, in this case the operation is used to change views
        Intent intent = new Intent(StartingScreenActivity.this, QuizActivity.class);
        startActivity(intent);

    }
}
