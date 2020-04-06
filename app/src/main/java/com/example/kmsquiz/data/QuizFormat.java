package com.example.kmsquiz.data;

import android.provider.BaseColumns;

public class QuizFormat {

    private QuizFormat(){}

    public static class UserTable implements BaseColumns{
        public static final String TableName = "Users";
        public static final String ColumnID = "UserId";
        public static final String ColumnPass = "Password";
        public static final String ColumnName = "Username";
    }

    public  static class QuestionTable implements  BaseColumns{
        public static final String TableName = "Question";
        public static final String ColumnNmb = "Number";
        public static final String ColumnTxt = "Text";
        public static final String ColumnPt = "Point";
        public static final String ColumnDiff = "Difficulty";
    }

    public static class AnswerTable implements BaseColumns{
        public static final String TableName = "Answer";
        public static final String ColumnTxt = "Text";
        public static final String ColumnQues = "quesNum";
    }


}

