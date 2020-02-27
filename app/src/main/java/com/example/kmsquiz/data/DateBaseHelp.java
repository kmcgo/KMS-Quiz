package com.example.kmsquiz.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.kmsquiz.Answer;
import com.example.kmsquiz.QuestionDB;
import com.example.kmsquiz.data.QuizFormat.*;
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
                UserTable.TableName + "( " +
                UserTable.ColumnName + "TEXT, " +
                UserTable.ColumnID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                UserTable.ColumnPass + "TEXT" + ")";

        final String sqlCreateQuestion = "CREATE TABLE " +
                QuestionTable.TableName + "( " +
                QuestionTable.ColumnNmb + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.ColumnTxt + "TEXT, " +
                QuestionTable.ColumnDiff + "TEXT, " +
                QuestionTable.ColumnPt + "INTEGER" +
                " )";

        final String sqlCreateAnswer = "CREATE TABLE " +
                AnswerTable.TableName + "( " +
                AnswerTable.ColumnTxt + "TEXT, " +
                AnswerTable.ColumnQues + "INTEGER " +
                "FOREIGN KEY(" + AnswerTable.ColumnQues + ") REFERENCES " +
                QuestionTable.TableName + "(" + QuestionTable.ColumnNmb + ")" + "ON DELETE CASCADE" +
                ")";
        db.execSQL(sqlCreateQuestion);
        db.execSQL(sqlCreateUser);
    }

    @Override
    public void onConfigure(SQLiteDatabase DB)
    {
        super.onConfigure(DB);
        DB.setForeignKeyConstraintsEnabled(true);
    }
    public void onUpgrade(SQLiteDatabase db, int oldv, int newv)
    {
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TableName);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TableName);
        onCreate(db);
    }

    private void addUser(User user)
    {
        ContentValues cv = new ContentValues();
        cv.put(UserTable.ColumnID, user.getId());
        cv.put(UserTable.ColumnName, user.getName());
        cv.put(UserTable.ColumnPass,user.getPass());
        db.insert(UserTable.TableName, null, cv);
    }

    private void addQuestion(QuestionDB ques)
    {
        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.ColumnDiff, ques.getDiff());
        cv.put(QuestionTable.ColumnNmb, ques.getNum());
        cv.put(QuestionTable.ColumnTxt, ques.getTxt());
        cv.put(QuestionTable.ColumnPt, ques.getPts());
        db.insert(QuestionTable.TableName, null, cv);
    }

    private void addAnswer(Answer ans){
        ContentValues cv = new ContentValues();
        cv.put(AnswerTable.ColumnTxt, ans.getTxt());
        cv.put(AnswerTable.ColumnQues, ans.getQuesNum());
        db.insert(AnswerTable.TableName, null, cv);
    }

    public List<QuestionDB> getAllQuestions()
    {
        List<QuestionDB> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionTable.TableName, null);

        if  (c.moveToFirst())
        {
            while (c.moveToNext())
            {
                QuestionDB q = new QuestionDB();
                q.setDiff(c.getString(c.getColumnIndex(QuestionTable.ColumnDiff)));
                q.setNum(c.getInt(c.getColumnIndex(QuestionTable.ColumnNmb)));
                q.setPts(c.getInt(c.getColumnIndex(QuestionTable.ColumnPt)));
                q.setTxt(c.getString(c.getColumnIndex(QuestionTable.ColumnTxt)));
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
        Cursor c = db.rawQuery("SELECT " + UserTable.ColumnID +
                ", " +  UserTable.ColumnName +
                " FROM " + UserTable.TableName, null);

        if  (c.moveToFirst())
        {
            while (c.moveToNext())
            {
                User u = new User();
                u.setName(c.getString(c.getColumnIndex(UserTable.ColumnName)));
                u.setId(c.getInt(c.getColumnIndex(UserTable.ColumnID)));
                userList.add(u);
            }
        }
        c.close();
        return userList;
    }
}
