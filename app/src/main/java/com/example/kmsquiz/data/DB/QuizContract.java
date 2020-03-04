package com.example.kmsquiz.data.DB;

import android.provider.BaseColumns;

public class QuizContract {

    private QuizContract() {
    }

    public static class CategoriesTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_categories";
        public static final String COLUMN_NAME = "name";
    }

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
        public static final String COLUMN_CATEGORY_ID = "category_id";

    }

    public static class QuestionDB implements BaseColumns {
        public static final String TableName = "Questions";
        public static final String ColumnQuestionId = "QuestId";
        public static final String ColumnQuizId = "QuizId";
        public static final String ColumnPoints = "Points";
        public static final String ColumnText = "Text";
    }

    public static class QuizTable implements BaseColumns {
        public static final String TableName  = "QUIZ";
        public static final String ColumnQuizID = "QuizId";
        public static final String ColumnTitle = "Title";
    }

    public static class QtHasTable implements BaseColumns {
        public static final String TableName = "Qthas";
        public static final String ColumnQuizId = "QuizId";
        public static final String ColumnQuestId = "QuestId";
        public static final String ColumnTotQuest = "TotQuest";
        public static final String ColumnTotPts = "TotPts";
    }

    public static class QzHasTable implements BaseColumns {
        public static final String TableName = "QzHas";
        public static final String ColumnQuestId = "QuestId";
        public static final String ColumnQuizID = "QuizId";
        public static final String ColumnAnswId = "AnswId";
        public static final String ColumnCorrect = "Correct";
    }

    public static class AnswerTable implements BaseColumns {
        public static final String TableName = "Answer";
        public static final String ColumnQuizId = "QuizId";
        public static final String ColumnAnswId = "AnswId";
    }

    public static class UserIdTable implements BaseColumns {
        public static final String TableName = "UserHist";
        public static final String ColumnQuestId = "QuestId";
        public static final String ColumnGivenId = "GivenId";
        public static final String ColumnQuizId = "QuizId";
        public static final String ColumnCorrectId = "Correct";
        public static final String ColumnAttempt = "Attempt";
        public static final String ColumnUserId = "UserId";
    }

    public static class UserHist implements BaseColumns {
        public static final String TableName = "UserInfo";
        public static final String ColumnPassword = "Pass";
        public static final String ColumnUserId = "UserId";
        public static final String ColumnUserName = "Name";
    }

    public static class IsAdmn implements BaseColumns {
        public static final String TableName = "IsAdmin";
        public static final String ColumnUserId = "UserId";
        public static final String ColumnAdmin = "Admin";
    }
}