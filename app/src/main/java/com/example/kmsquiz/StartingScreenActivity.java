package com.example.kmsquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.kmsquiz.data.DB.Category;
import com.example.kmsquiz.data.DB.QuizDBHelp;


public class StartingScreenActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String EXTRA_CATEGORY_ID = "extraCategoryID";
    public static final String EXTRA_CATEGORY_NAME = "extraCategoryName";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private TextView textViewHighscore;
    private Spinner spinnerCategory;

    private int highscore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);//Sets starting layout based on associated xml file

        //Bottom menu bar for settings, user progress, etc
        BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        item.setChecked(true);
                        break;
                    case R.id.navigation_user:
                        Intent intent2 = new Intent(StartingScreenActivity.this, UserActivity.class);
                        startActivity(intent2);
                        item.setChecked(false);


                        break;
                    case R.id.navigation_admin:
                        Intent intent3 = new Intent(StartingScreenActivity.this, AdminActivity.class);
                        startActivity(intent3);
                        item.setChecked(false);
                        break;
                }
                return false;
            }
        });


        textViewHighscore = findViewById(R.id.text_view_highscore); // intit hs, and category
        spinnerCategory = findViewById(R.id.spinner_category);

        loadCategories(); // load values from data base
        loadHighscore();
        loadProgress();

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

        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();
        int categoryID = selectedCategory.getId();
        String categoryName = selectedCategory.getName();

        intent.putExtra(EXTRA_CATEGORY_ID, categoryID); // save to the intent
        intent.putExtra(EXTRA_CATEGORY_NAME, categoryName);

        startActivityForResult(intent, REQUEST_CODE_QUIZ);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //updater for hs
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                if (score > highscore) {
                    updateHighscore(score);
                }
            }
        }
    }

    private void loadProgress(){
        QuizDBHelp dbHelper = QuizDBHelp.getInstance(this); // get the instance of quiz helper class
        int prog = dbHelper.getprogress();
        int numAnswer = 1;
        ProgressBar simpleProgressBar=findViewById(R.id.overall_progress); // initiate the progress bar
        simpleProgressBar.setMax(prog); // 100 maximum value for the progress value
        simpleProgressBar.setProgress(numAnswer); // 50 default progress value for the progress bar
    }

    private void loadCategories() {
        QuizDBHelp dbHelper = QuizDBHelp.getInstance(this); // get the instance of quiz helper class
        ArrayList<Category> categories = dbHelper.getAllCategories(); // get categories from db

        ArrayAdapter<Category> adapterCategories = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategories); // add category to the spinner
    }


    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Highscore: " + highscore); // display hs
    }

    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;                                                       // update hs variable
        textViewHighscore.setText("Highscore: " + highscore);                           // display hs

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }
}
