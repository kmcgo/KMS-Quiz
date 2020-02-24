package com.example.kmsquiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {


    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 30000;

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewDifficulty;
    private TextView textViewCategory;
    private TextView textViewCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private ArrayList<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;

    private long backPressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz); //set view

        //init variables
        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewCategory = findViewById(R.id.text_view_category);
        textViewDifficulty = findViewById(R.id.text_view_difficulty);
        textViewCountDown = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);

        //set colors
        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCd = textViewCountDown.getTextColors();

        Intent intent = getIntent();

        //set category and difficulty from spinner
        int categoryID = intent.getIntExtra(StartingScreenActivity.EXTRA_CATEGORY_ID, 0);
        String categoryName = intent.getStringExtra(StartingScreenActivity.EXTRA_CATEGORY_NAME);
        String difficulty = intent.getStringExtra(StartingScreenActivity.EXTRA_DIFFICULTY);

        //display diff and cat
        textViewCategory.setText("Category: " + categoryName);
        textViewDifficulty.setText("difficulty: " + difficulty);

        //if first instance init quiz
        if (savedInstanceState == null) {
            QuizDBHelp dbHelper = new QuizDBHelp(this);
            questionList = dbHelper.getQuestions(categoryID, difficulty);
            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);

            showNextQuestion();
        } else { //else get saved state
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCountTotal = questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionCounter - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            if (!answered) {
                startCountDown();
            } else {
                updateCountDownText();
                showSolution();
            }
        }

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb); //set colors to defaults
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck(); //clear previous selection

        if (questionCounter < questionCountTotal) { // if not the end of the quiz
            currentQuestion = questionList.get(questionCounter); //update current question

            textViewQuestion.setText(currentQuestion.getQuestion());  //get question
            rb1.setText(currentQuestion.getOption1()); //get choices
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++; // update counter
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal); //update totals + score
            answered = false; //reset answer
            buttonConfirmNext.setText("Confirm"); // button to move on

            timeLeftInMillis = COUNTDOWN_IN_MILLIS; // reset timer
            startCountDown(); //start timer
        } else {
            finishQuiz(); // else end quiz
        }
    }


    private void startCountDown() { //calls for the start of the countdown timer
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() { //call for updating the countdown timer
        int minutes = (int) (timeLeftInMillis / 1000) / 60; //minutes hand
        int seconds = (int) (timeLeftInMillis / 1000) % 60; //seconds hand

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds); //set format

        textViewCountDown.setText(timeFormatted); //set the text to display

        if (timeLeftInMillis < 10000) { //change color if low on time
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCd); // else keep black
        }
    }

    private void checkAnswer() {
        answered = true; // mark as answered

        countDownTimer.cancel(); // stop clock

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId()); // find the selected button
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1; //get index of selected button

        if (answerNr == currentQuestion.getAnswerNr()) { // if correct
            score++; // increment score
            textViewScore.setText("Score: " + score); // update score text
        }

        showSolution(); //display score
    }


    private void showSolution() {
        rb1.setTextColor(Color.RED); // default all look wrong
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) { // correct one is changed to green
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 is correct");
                break;
        }

        if (questionCounter < questionCountTotal) { // if end of quiz
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }
    }

    private void finishQuiz() { // protocol for finishing a quiz
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score); // set score
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() { // protocol for back button press
        if (backPressedTime + 2000 > System.currentTimeMillis()) { // if second press
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show(); // if first press
        }

        backPressedTime = System.currentTimeMillis(); //log the time of press
    }

}
