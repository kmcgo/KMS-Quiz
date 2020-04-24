package com.example.kmsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kmsquiz.data.Category;

public class AddQuizActivity extends AppCompatActivity {
    public static int quizID = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);

        Intent intent = getIntent();
        String test = intent.getStringExtra("finish_quiz_key");
        System.out.println(test);
        final EditText quizName = (EditText)findViewById(R.id.quiz_name);



        Button addQuestions = findViewById(R.id.btn_continue);
        addQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddQuizActivity.this, AddQuestionActivity.class);
                Category newQuiz = new Category(quizName.getText().toString());
                intent.putExtra("new_quiz_key", newQuiz);
                startActivity(intent);
            }
        });

    }
}
