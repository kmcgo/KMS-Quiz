package com.example.kmsquiz;

import android.content.Intent;
import android.os.Bundle;

import com.example.kmsquiz.data.Category;
import com.example.kmsquiz.data.DB.QuizDBHelp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ManageQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_quiz);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //LISTVIEW FOR QUESTIONS
        QuizDBHelp dbHelper = QuizDBHelp.getInstance(this); // get the instance of quiz helper class
        ArrayList<Category> categories = dbHelper.getAllCategories(); // get categories from db
        ArrayAdapter<Category> adapterCategories = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, categories);
        ListView listView = findViewById(R.id.listview_adminQuestions);
        listView.setAdapter(adapterCategories);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageQuizActivity.this, AddQuizActivity.class);
                startActivity(intent);
            }
        });
    }

}
