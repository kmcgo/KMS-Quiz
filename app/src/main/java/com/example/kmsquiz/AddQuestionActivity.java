package com.example.kmsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kmsquiz.data.Category;
import com.example.kmsquiz.data.DB.Question;
import com.example.kmsquiz.data.DB.QuizDBHelp;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {
    List<Question> questions = new ArrayList<Question>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        final QuizDBHelp dbHelper = QuizDBHelp.getInstance(this);

        Intent intent = getIntent();
        final Category newQuiz = intent.getParcelableExtra("new_quiz_key");

        final EditText question = findViewById(R.id.question);
        final EditText answer1 = findViewById(R.id.answer1);
        final EditText answer2 = findViewById(R.id.answer2);
        final EditText answer3 = findViewById(R.id.answer3);

        Button finish = findViewById(R.id.btn_finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddQuestionActivity.this, ManageQuizActivity.class);
                dbHelper.addCategory(newQuiz);
                dbHelper.addQuestions(questions);
                startActivity(intent);
            }
        });

        Button addMore = findViewById(R.id.btn_addMoreQuestions);
        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddQuestionActivity.this, AddQuestionActivity.class);
//                String question, String option1, String option2, String option3,
//                int answerNr, int categoryID
                Question q = new Question(question.getText().toString(), answer1.getText().toString(), answer2.getText().toString(), answer3.getText().toString(), 1, newQuiz.getId());
                questions.add(q);
                intent.putExtra("new_quiz_key", newQuiz);
                startActivity(intent);
            }
        });
    }
}
