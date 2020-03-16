package com.example.kmsquiz.data.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.kmsquiz.data.User;
import com.example.kmsquiz.data.DB.QuizContract.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DateBaseHelp extends SQLiteOpenHelper {
    private static String DBname = "KMS_Quiz";
    private static int DBVersion = 1;

    private static DateBaseHelp instance;
    private SQLiteDatabase db;

    private DateBaseHelp(Context c)
    {
        super(c, DBname, null, DBVersion);
    }

    public static synchronized DateBaseHelp getInstance(Context c)
    {
        if (instance == null)
        {
            instance = new DateBaseHelp(c.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        this.db = db;
        final String sqlCreateUser = "CREATE TABLE " +
                UserIdTable.TableName + "( " +
                UserIdTable.ColumnUserName + "TEXT, " +
                UserIdTable.ColumnUserId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                UserIdTable.ColumnPassword + "TEXT" + ")";

        final String sqlCreateQuestion = "CREATE TABLE " +
                QuestionDBTable.TableName + "( " +
                QuestionDBTable.ColumnQuestionId + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionDBTable.ColumnText + "TEXT, " +
                QuestionDBTable.ColumnPoints + "INTEGER, " +
                QuestionDBTable.ColumnQuizId + "INTEGER, " +
                "FOREIGN KEY(" + QuestionDBTable.ColumnQuizId +") REFERENCES " + QuizTable.TableName +
                "(" + QuizTable.ColumnQuizID + ") )";

        final String sqlCreateQuiz = "CREATE TABLE " +
                QuizTable.TableName + "( " +
                QuizTable.ColumnTitle + "TEXT, " +
                QuizTable.ColumnQuizID + "INTEGER PRIMARY KEY AUTOINCREMENT" +
                " )";

        final String sqlCreateAns = "CREATE TABLE " +
                AnswerTable.TableName + "( " +
                AnswerTable.ColumnAnswId + "INTEGER, " +
                AnswerTable.ColumnQuizId + "INTEGER, " +
                "FOREIGN KEY (" + AnswerTable.ColumnQuizId + ") REFERENCES " + QuizTable.TableName +
                "(" + QuizTable.ColumnQuizID + ") )";

        final String sqlCreateQtHas = "CREATE TABLE " +
                QtHasTable.TableName + "( " +
                QtHasTable.ColumnQuizID + "INTEGER, " +
                QtHasTable.ColumnQuestId + "INTEGER, " +
                QtHasTable.ColumnAnswId + "INTEGER, " +
                QtHasTable.ColumnCorrect + "INTEGER, " +
                "FOREIGN KEY (" + QtHasTable.ColumnQuizID + ") REFERENCES " + QuizTable.TableName +
                "(" + QuizTable.ColumnQuizID + "), " +
                "FOREIGN KEY (" + QtHasTable.ColumnQuestId + ") REFERENCES " + QuestionDBTable.TableName +
                "(" + QuestionDBTable.ColumnQuizId + "), " +
                "FOREIGN KEY (" + QtHasTable.ColumnAnswId + ") REFERENCES " + AnswerTable.TableName +
                "(" + AnswerTable.ColumnAnswId + ") )";

        final String sqlCreateUserHist = "CREATE TABLE " +
                UserHistTable.TableName + "( " +
                UserHistTable.ColumnUserId + "INTEGER, " +
                UserHistTable.ColumnAttempt + "INTEGER," +
                UserHistTable.ColumnQuizId + "INTEGER, " +
                UserHistTable.ColumnQuestId + "INTEGER, " +
                UserHistTable.ColumnGivenId + "INTEGER, " +
                UserHistTable.ColumnCorrectId + "INTEGER, " +
                "FOREIGN KEY (" + UserHistTable.ColumnUserId + ") REFERENCES " + UserIdTable.TableName +
                "(" + UserIdTable.ColumnUserId + "), " +
                "FOREIGN KEY (" + UserHistTable.ColumnQuizId + ") REFERENCES " + QuizTable.TableName +
                "(" + QuizTable.ColumnQuizID + "), " +
                "FOREIGN KEY (" + QtHasTable.ColumnQuizID + ") REFERENCES " + QuizTable.TableName +
                "(" + QuizTable.ColumnQuizID + "), " +
                "FOREIGN KEY (" + UserHistTable.ColumnQuestId + ") REFERENCES " + QuestionDBTable.TableName +
                "(" + QuestionDBTable.ColumnQuizId + "), " +
                "FOREIGN KEY (" + UserHistTable.ColumnGivenId + ") REFERENCES " + AnswerTable.TableName +
                "(" + AnswerTable.ColumnAnswId + ")," +
                "FOREIGN KEY (" + UserHistTable.ColumnCorrectId + ") REFERENCES " + AnswerTable.TableName +
                "(" + AnswerTable.ColumnAnswId + ") )";

        final String sqlCreateQzHas = "CREATE TABLE " +
                QzHasTable.TableName + "( " +
                QzHasTable.ColumnQuestId + "INTEGER, " +
                QzHasTable.ColumnQuizId + "INTEGER, " +
                QzHasTable.ColumnTotPts + "INTEGER, " +
                QzHasTable.ColumnTotQuest + "INTEGER, " +
                "FOREIGN KEY (" + QzHasTable.ColumnQuizId + ") REFERENCES " + QuizTable.TableName +
                "(" + QuizTable.ColumnQuizID + "), " +
                "FOREIGN KEY (" + QzHasTable.ColumnQuestId + ") REFERENCES " + QuestionDBTable.TableName +
                "(" + QuestionDBTable.ColumnQuestionId + ") )";

        final String sqlCreateIsAdmin = "CREATE TABLE " +
                IsAdmnTable.TableName + "( " +
                IsAdmnTable.ColumnUserId + "INTEGER, " +
                IsAdmnTable.ColumnAdmin + "BOOLEAN, " +
                "FOREIGN KEY (" + IsAdmnTable.ColumnUserId + ") REFERENCES " + UserIdTable.TableName +
                "(" + UserIdTable.ColumnUserId + ") )";

        db.execSQL(sqlCreateQuestion);
        db.execSQL(sqlCreateUser);
        db.execSQL(sqlCreateQuiz);
        db.execSQL(sqlCreateQtHas);
        db.execSQL(sqlCreateAns);
        db.execSQL(sqlCreateUserHist);
        db.execSQL(sqlCreateQzHas);
        db.execSQL(sqlCreateIsAdmin);
    }

    @Override
    public void onConfigure(SQLiteDatabase DB)
    {
        super.onConfigure(DB);
        DB.setForeignKeyConstraintsEnabled(true);
    }
    public void onUpgrade(SQLiteDatabase db, int oldv, int newv)
    {
        db.execSQL("DROP TABLE IF EXISTS " + UserIdTable.TableName);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionDBTable.TableName);
        onCreate(db);
    }

    private void addUser(User user)
    {
        ContentValues cv = new ContentValues();
        cv.put(UserIdTable.ColumnUserId, user.getId());
        cv.put(UserIdTable.ColumnUserName, user.getName());
        cv.put(UserIdTable.ColumnPassword,user.getPass());
        db.insert(UserIdTable.TableName, null, cv);
    }

    private void addAdmin(IsAdmin ad)
    {
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + UserIdTable.TableName + " WHERE " +
                UserIdTable.ColumnUserId + "= ?", new String[] {String.valueOf(ad.getUserId())} );
        c.moveToFirst();
        int test = c.getInt(c.getColumnIndex(UserIdTable.ColumnUserId));
        //if (test != Integer.getInteger(ad.getUserId()))
        {
          //  System.out.println();
        }
        ContentValues cv = new ContentValues();
        cv.put(IsAdmnTable.ColumnAdmin, ad.isAdmin());
        cv.put(IsAdmnTable.ColumnUserId, ad.getUserId());
        db.insert(IsAdmnTable.TableName, null, cv);
    }


    private void addQuestion(QuestionDB ques)
    {
        ContentValues cv = new ContentValues();
        cv.put(QuestionDBTable.ColumnQuestionId, ques.getId());
        cv.put(QuestionDBTable.ColumnText, ques.getTxt());
        cv.put(QuestionDBTable.ColumnPoints, ques.getTotPts());
        cv.put(QuestionDBTable.ColumnQuizId, ques.getQuizId());
        db.insert(QuestionDBTable.TableName, null, cv);
    }

    private void addAnswer(Answer ans){
        ContentValues cv = new ContentValues();
        cv.put(AnswerTable.ColumnText, ans.getTxt());
        cv.put(AnswerTable.ColumnQuizId, ans.getQuesNum());
        cv.put(AnswerTable.ColumnAnswId, ans.getAnsId());
        db.insert(AnswerTable.TableName, null, cv);
    }

    private void addQuiz(QuizDB q) {
        ContentValues cv = new ContentValues();
        cv.put(QuizTable.ColumnQuizID, q.getQuizId());
        cv.put(QuizTable.ColumnTitle, q.getTitle());
        db.insert(QuizTable.TableName, null,  cv);
    }


    public List<QuestionDB> getQuizQuestions(int quizid)
    {
        List<QuestionDB> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionDBTable.TableName + " WHERE " + QuestionDBTable.ColumnQuizId + "= ?", new String[] {String.valueOf(quizid)});

        if  (c.moveToFirst())
        {
            while (c.moveToNext())
            {
                QuestionDB q = new QuestionDB();
                q.setId(c.getInt(c.getColumnIndex(QuestionDBTable.ColumnQuestionId)));
                q.setTotPts(c.getInt(c.getColumnIndex(QuestionDBTable.ColumnPoints)));
                q.setTxt(c.getString(c.getColumnIndex(QuestionDBTable.ColumnText)));
                q.setQuizId(c.getInt(c.getColumnIndex(QuestionDBTable.ColumnQuizId)));
                questionList.add(q);
            }
        }
        c.close();
        return questionList;
    }

    public List<QuestionDB> getAllQuestions()
    {
        List<QuestionDB> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionDBTable.TableName, null);

        if  (c.moveToFirst())
        {
            while (c.moveToNext())
            {
                QuestionDB q = new QuestionDB();
                q.setId(c.getInt(c.getColumnIndex(QuestionDBTable.ColumnQuestionId)));
                q.setTotPts(c.getInt(c.getColumnIndex(QuestionDBTable.ColumnPoints)));
                q.setTxt(c.getString(c.getColumnIndex(QuestionDBTable.ColumnText)));
                q.setQuizId(c.getInt(c.getColumnIndex(QuestionDBTable.ColumnQuizId)));
                questionList.add(q);
            }
        }
        c.close();
        return questionList;
    }

    public List<User> getAllUser()
    {
        List<User> userList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + UserIdTable.TableName, null);

        if  (c.moveToFirst())
        {
            while (c.moveToNext())
            {
                User u = new User();
                u.setName(c.getString(c.getColumnIndex(UserIdTable.ColumnUserName)));
                u.setId(c.getInt(c.getColumnIndex(UserIdTable.ColumnUserId)));
                userList.add(u);
            }
        }
        c.close();
        return userList;
    }
}
