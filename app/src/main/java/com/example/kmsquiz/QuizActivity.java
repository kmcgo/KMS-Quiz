package com.example.kmsquiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kmsquiz.data.DB.Answer;
import com.example.kmsquiz.data.DB.DateBaseHelp;
import com.example.kmsquiz.data.DB.QtHas;
import com.example.kmsquiz.data.DB.Question;
import com.example.kmsquiz.data.DB.QuestionDB;
import com.example.kmsquiz.data.DB.QuizDBHelp;

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

    private ArrayList<Question> questionList;   // comment out this for test db, use for known db
    //private ArrayList<QuestionDB> questionList;  // comment out next three for known db, use for test db
    //private ArrayList<Answer> answerList;
    //private ArrayList<QtHas> qtHasList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;    // comment out this for test db, use for known db
    //private QuestionDB currentQuestion;   // comment out this for known db, use for test db

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

        //set category from spinner
        int categoryID = intent.getIntExtra(StartingScreenActivity.EXTRA_CATEGORY_ID, 0);
        String categoryName = intent.getStringExtra(StartingScreenActivity.EXTRA_CATEGORY_NAME);

        //display cat
        textViewCategory.setText("Category: " + categoryName);
        //if first instance init quiz
        if (savedInstanceState == null) {
            QuizDBHelp dbHelper = new QuizDBHelp(this); // comment out this for test db, use for known db
            //DateBaseHelp dbHelper = new DateBaseHelp(this);   // comment out this for know db, use for test db

            questionList = dbHelper.getQuestions(categoryID);  // comment out this for test db, use for known db
            //questionList = dbHelper.getAllQuestions();    // comment out next three for known db, use for test db
            //answerList = dbHelper.getAnswers(1);
            //qtHasList = dbHelper.getQtA();
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

            textViewQuestion.setText(currentQuestion.getQuestion());  //get question    comment out this block for test db use for safe db
            rb1.setText(currentQuestion.getOption1()); //get choices
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            /*
            ArrayList<String> answers = new ArrayList<>();     //comment out this block for known db use for test db
            for (int i = 0; i < answerList.size(); i++)
            {
                Answer a = answerList.get(i);
                if (a.getQuesNum() == questionCounter)
                {
                    answers.add(a.getTxt());
                }
            }
            textViewQuestion.setText(currentQuestion.getTxt());
            rb1.setText(answers.get(0));
            rb2.setText(answers.get(1));
            rb2.setText(answers.get(2));
             */

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

    private int ranSelect;
    private void updateCountDownText() { //call for updating the countdown timer
        int minutes = (int) (timeLeftInMillis / 1000) / 60; //minutes hand
        int seconds = (int) (timeLeftInMillis / 1000) % 60; //seconds hand
        Random rand = new Random();
        Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);

        if(seconds == 23){
            switch (ranSelect) {
                case 1:
                    rb1.setAlpha(0);
                    break;
                case 2:
                    rb2.setAlpha(0);
                    break;
                case 3:
                    rb3.setAlpha(0);
                    break;
            }
        }

        if(seconds == 25) {
            ranSelect = rand.nextInt(3) + 1;;

            if(ranSelect == currentQuestion.getAnswerNr()){    // // if correct  //replace with currentQuestion.answerNr for safe, getAnswerNr for test
                while(ranSelect == currentQuestion.getAnswerNr() ) { // replaced by currentQuestion.getAnswerNr
                    ranSelect = rand.nextInt(3) + 1;
                }
            }
                switch (ranSelect) {
                    case 1:
                        rb1.startAnimation(animFadeOut);
                        break;
                    case 2:
                        rb2.startAnimation(animFadeOut);
                        break;
                    case 3:
                        rb3.startAnimation(animFadeOut);

                        break;
            }
        }
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds); //set format

        textViewCountDown.setText(timeFormatted); //set the text to display

        if (timeLeftInMillis < 10000) { //change color if low on time
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCd); // else keep black
        }
    }

/*   private int getAnswerNr()   // to be used with test database, not safe db
    {
        int answercorrect = 0;
        for (int i = 0; i < qtHasList.size(); i++)
        {
            QtHas qt = qtHasList.get(i);
            if (qt.getQuesID() == currentQuestion.getNum())
            {
                answercorrect = qt.getCorrect();
            }
        }
        return answercorrect;
    }
*/
    private void checkAnswer() {
        answered = true; // mark as answered

        countDownTimer.cancel(); // stop clock

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId()); // find the selected button
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1; //get index of selected button

        if (answerNr == currentQuestion.getAnswerNr()) { // if correct  //replace with currentQuestion.answerNr for safe, getAnswerNr for test
            score++; // increment score
            textViewScore.setText("Score: " + score); // update score text
        }

        showSolution(); //display score
        //Makes the invisible answer reappear
        switch (ranSelect) {
            case 1:
                rb1.setAlpha(1);
                break;
            case 2:
                rb2.setAlpha(1);
                break;
            case 3:
                rb3.setAlpha(1);
                break;
        }
    }


    private void showSolution() {
        rb1.setTextColor(Color.RED); // default all look wrong
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) { // correct one is changed to green  //replace with currentQuestion.getAnswerNr for safe, getAnserNr for test db
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
