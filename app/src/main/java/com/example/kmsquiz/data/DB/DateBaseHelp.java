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
    private static String DBname = "KMS_Quiz.db";
    private static int DBVersion = 1;

    private static DateBaseHelp instance;
    private SQLiteDatabase db;

    public DateBaseHelp(Context c)
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
                UserIdTable.ColumnUserId + " INTEGER PRIMARY KEY AUTOINCREMENT )";

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
                QuizTable.ColumnQuizID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                QuizTable.ColumnTotPts + "INTEGER, " +
                QuizTable.ColumnTotQues + "INTEGER" +
                " )";

        final String sqlCreateAns = "CREATE TABLE " +
                AnswerTable.TableName + "( " +
                AnswerTable.ColumnAnswId + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AnswerTable.ColumnQuestId + "INTEGER, " +
                AnswerTable.ColumnQuizId + "INTEGER, " +
                AnswerTable.ColumnText + "TEXT, " +
                "FOREIGN KEY (" + AnswerTable.ColumnQuizId + ") REFERENCES " + QuizTable.TableName +
                "(" + QuizTable.ColumnQuizID + ")," +
                "FOREIGN KEY (" + AnswerTable.ColumnQuestId + ") REFERENCES " + QuestionDBTable.TableName +
                " )";

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
                "FOREIGN KEY (" + QzHasTable.ColumnQuizId + ") REFERENCES " + QuizTable.TableName +
                "(" + QuizTable.ColumnQuizID + "), " +
                "FOREIGN KEY (" + QzHasTable.ColumnQuestId + ") REFERENCES " + QuestionDBTable.TableName +
                "(" + QuestionDBTable.ColumnQuestionId + ") )";

        final String sqlCreateIsAdmin = "CREATE TABLE " +
                IsAdmnTable.TableName + "( " +
                IsAdmnTable.ColumnUserId + "INTEGER, " +
                IsAdmnTable.ColumnAdmin + "INTEGER, " +
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
        //createTrigger(db);
        fillQuestionsTable();
    }

    private void createTrigger(SQLiteDatabase db)
    {
        final String sqlInsertQuizHas = "CREATE TRIGGER InsertQuizHas AFTER INSERT " +
                " ON " + QuizTable.TableName +
                " BEGIN " +
                "   INSERT INTO " + QzHasTable.TableName + "(" +
                QzHasTable.ColumnQuestId +", " + QzHasTable.ColumnQuizId +
                 ") VALUES (new.QuestId, new.QuizId);" +
                " END;" ;

        final String sqlUpdateQuizHas = "CREATE TRIGGER UpdateHas AFTER INSERT " +
                " ON " + QuestionDBTable.TableName +
                " BEGIN " +
                "   UPDATE " + QuizTable.TableName +
                "   SET " + QuizTable.ColumnTotQues + " = " + QuizTable.ColumnTotPts + " + old_" + QuestionDBTable.ColumnPoints +
                "   WHERE " + QuestionDBTable.ColumnQuizId + " = " + QuizTable.ColumnQuizID + ";" +
                " END;" ;

        final String sqlInsertQuestHas = "CREATE TRIGGER InsertQuestHas AFTER INSERT " +
                "ON " + QuestionDBTable.TableName +
                " BEGIN " +
                "   INSERT INTO " + QtHasTable.TableName + "(" +
                QtHasTable.ColumnQuizID + ", " + QtHasTable.ColumnQuizID + ", " + QtHasTable.ColumnAnswId +
                ", " + QtHasTable.ColumnCorrect + ") VALUES (new_" + QuestionDBTable.ColumnQuizId + ", new_" +
                QuestionDBTable.ColumnQuestionId + ", " + null + ",  " + "null);" +
                " END;";

        final String sqlUpdateQuestHas = "CREATE TRIGGER UpdateQuestHas AFTER INSERT" +
                " ON " + AnswerTable.TableName +
                " BEGIN " +
                "   INSERT INTO " + QtHasTable.TableName + "(" +
                QtHasTable.ColumnQuestId + ", " + QtHasTable.ColumnAnswId + ", " +QtHasTable.ColumnCorrect +
                ") VALUES (new_" + AnswerTable.ColumnQuestId + ", new_" + AnswerTable.ColumnQuizId + ", new_" +
                AnswerTable.ColumnText + ") ; " +
                "END ;";


        db.execSQL(sqlInsertQuizHas);
        db.execSQL(sqlUpdateQuizHas);
        db.execSQL(sqlInsertQuestHas);
        db.execSQL(sqlUpdateQuestHas);
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
        db.execSQL("DROP TABLE IF EXISTS " + QuizTable.TableName);
        db.execSQL("DROP TABLE IF EXISTS " + QtHasTable.TableName);
        db.execSQL("DROP TABLE IF EXISTS " + AnswerTable.TableName);
        db.execSQL("DROP TABLE IF EXISTS " + UserHistTable.TableName);
        db.execSQL("DROP TABLE IF EXISTS " + IsAdmnTable.TableName);
        db.execSQL("DROP TABLE IF EXISTS " + QzHasTable.TableName);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        QuizDB q = new QuizDB();
        q.setTitle("a");
        addQuiz(q);
        QuestionDB[] q1 = new QuestionDB[6];
        Answer[][] a = new Answer[6][3];
        for (int i = 0; i < 6; i++)
        {
            q1[i].setTotPts(i);
            q1[i].setQuizId(1);
        }
        q1[0].setTxt("What is the recommended dosing regimen for Opdivo in combo w/lpi in patients with intermediate/poor risk advanced RCC?");
        q1[1].setTxt("What was the hazard ratio for PFS in favor of Opdivo plus IPI compared with sunitinib in the Checkmate 214 immediately?");
        q1[2].setTxt("Patients were excluded from Checkmate 025 if they had which of the following?");
        q1[3].setTxt("What were the major efficacy outcome measures in Checkmate 214?");
        q1[4].setTxt("Non existing, Easy: A is correct");
        q1[5].setTxt("Non existing, Medium: B is correct");
        for (int i = 0; i < 6; i++)
        {
            addQuestion(q1[i]);
        }
        fillAnserTable();
    }

    private void fillAnserTable()
    {
        Answer[][] a = new Answer[6][3];
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                a[i][j].setQuesNum(i+1);
            }
        }
        a[0][0].setTxt("3mg/kg admin as an IV infusion over 30 minutes, followed by IPI 1mg/kg admin as an IV infusion 4 weeks for 4 doses");
        a[0][1].setTxt("5mg/kg admin as an IV infusion over 10 minutes, followed by IPI 1mg/kg admin as an IV infusion 5 weeks for 3 doses");
        a[0][2].setTxt("3mg/kg admin as an IV infusion over 60 minutes, followed by IPI 1mg/kg admin as an IV infusion 8 weeks for 8 doses");
        a[1][0].setTxt("0.98");
        a[1][1].setTxt("0.28");
        a[1][2].setTxt("0.82");
        a[2][0].setTxt("No previous health issues");
        a[2][1].setTxt("Prior Treatment with an mTOR inhibitor");
        a[2][2].setTxt("T-Rex arms");
        a[3][0].setTxt("Confirmed ORR, PFS, OS");
        a[3][1].setTxt("Unconfirmed ORR, FFS, and OS");
        a[3][2].setTxt("FFS and S OL");
        a[4][0].setTxt("A");
        a[4][1].setTxt("B");
        a[4][2].setTxt("C");
        a[5][0].setTxt("A");
        a[5][1].setTxt("B");
        a[5][2].setTxt("C");
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                addAnswer(a[i][j]);
            }
        }

    }
    private void addUser(User user)
    {
        ContentValues cv = new ContentValues();
        cv.put(UserIdTable.ColumnUserName, user.getName());
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
        cv.put(QuestionDBTable.ColumnText, ques.getTxt());
        cv.put(QuestionDBTable.ColumnPoints, ques.getTotPts());
        cv.put(QuestionDBTable.ColumnQuizId, ques.getQuizId());
        db.insert(QuestionDBTable.TableName, null, cv);
    }


    private void addAnswer(Answer ans){
        ContentValues cv = new ContentValues();
        cv.put(AnswerTable.ColumnText, ans.getTxt());
        cv.put(AnswerTable.ColumnQuizId, ans.getQuesNum());
        db.insert(AnswerTable.TableName, null, cv);
    }

    private void addQuiz(QuizDB q) {
        ContentValues cv = new ContentValues();
        cv.put(QuizTable.ColumnTitle, q.getTitle());
        db.insert(QuizTable.TableName, null,  cv);
    }


    public ArrayList<Answer> getAnswers(int QuesId)
    {
        ArrayList<Answer> answerArrayList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + AnswerTable.TableName + " WHERE " + QuestionDBTable.ColumnQuestionId + "= ?",  new String[] {String.valueOf(QuesId)});

        if (c.moveToFirst())
        {
            while(c.moveToNext())
            {
                Answer a = new Answer();
                a.setAnsId(c.getInt(c.getColumnIndex(AnswerTable.ColumnAnswId)));
                a.setTxt(c.getString(c.getColumnIndex(AnswerTable.ColumnText)));
                a.setQuesNum(c.getInt(c.getColumnIndex(AnswerTable.ColumnQuestId)));
                a.setQuizNum(c.getInt(c.getColumnIndex(AnswerTable.ColumnQuizId)));
                answerArrayList.add(a);
            }
        }
        c.close();
        return answerArrayList;
    }

    public ArrayList<Answer> getAllAnswers()
    {
        ArrayList<Answer> answerArrayList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + AnswerTable.TableName, null);

        if (c.moveToFirst())
        {
            while(c.moveToNext())
            {
                Answer a = new Answer();
                a.setAnsId(c.getInt(c.getColumnIndex(AnswerTable.ColumnAnswId)));
                a.setTxt(c.getString(c.getColumnIndex(AnswerTable.ColumnText)));
                a.setQuesNum(c.getInt(c.getColumnIndex(AnswerTable.ColumnQuestId)));
                a.setQuizNum(c.getInt(c.getColumnIndex(AnswerTable.ColumnQuizId)));
                answerArrayList.add(a);
            }
        }
        c.close();
        return answerArrayList;
    }

    public ArrayList<QuestionDB> getQuizQuestions(int quizid)
    {
        ArrayList<QuestionDB> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionDBTable.TableName + " WHERE " + QuestionDBTable.ColumnQuizId + "= ?", new String[] {String.valueOf(quizid)});

        if  (c.moveToFirst())
        {
            while (c.moveToNext())
            {
                QuestionDB q = new QuestionDB(null);
                q.setNum(c.getInt(c.getColumnIndex(QuestionDBTable.ColumnQuestionId)));
                q.setTotPts(c.getInt(c.getColumnIndex(QuestionDBTable.ColumnPoints)));
                q.setTxt(c.getString(c.getColumnIndex(QuestionDBTable.ColumnText)));
                q.setQuizId(c.getInt(c.getColumnIndex(QuestionDBTable.ColumnQuizId)));
                questionList.add(q);
            }
        }
        c.close();
        return questionList;
    }

    public ArrayList<IsAdmin> getAdmin(int userId)
    {
        ArrayList<IsAdmin> admins = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + IsAdmnTable.TableName + " WHERE " + IsAdmnTable.ColumnUserId + " = ?", new String[] {String.valueOf(userId)});

        if (c.moveToFirst())
        {
            while (c.moveToNext())
            {
                IsAdmin a = new IsAdmin();
                a.setAdmin(1);
                a.setUserId(c.getInt(c.getColumnIndex(IsAdmnTable.ColumnUserId)));
                admins.add(a);
            }
        }
        c.close();
        return admins;
    }

    public ArrayList<QuestionDB> getAllQuestions()
    {
        ArrayList<QuestionDB> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionDBTable.TableName, null);

        if  (c.moveToFirst())
        {
            while (c.moveToNext())
            {
                QuestionDB q = new QuestionDB(null);
                q.setNum(c.getInt(c.getColumnIndex(QuestionDBTable.ColumnQuestionId)));
                q.setTotPts(c.getInt(c.getColumnIndex(QuestionDBTable.ColumnPoints)));
                q.setTxt(c.getString(c.getColumnIndex(QuestionDBTable.ColumnText)));
                q.setQuizId(c.getInt(c.getColumnIndex(QuestionDBTable.ColumnQuizId)));
                questionList.add(q);
            }
        }
        c.close();
        return questionList;
    }

    public ArrayList<User> getAllUser()
    {
        ArrayList<User> userList = new ArrayList<>();
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

    public ArrayList<QtHas> getQtA()
    {
        ArrayList<QtHas> qtHas = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QtHasTable.TableName, null);
        if  (c.moveToFirst())
        {
            while (c.moveToNext())
            {
                QtHas qt = new QtHas();
                qt.setAnsId(c.getInt(c.getColumnIndex(QtHasTable.ColumnAnswId)));
                qt.setCorrect(c.getInt(c.getColumnIndex(QtHasTable.ColumnCorrect)));
                qt.setQuesID(c.getInt(c.getColumnIndex(QtHasTable.ColumnQuestId)));
                qt.setQuizId(c.getInt(c.getColumnIndex(QtHasTable.ColumnQuizID)));
                qtHas.add(qt);
            }
        }
        c.close();
        return qtHas;
    }

}
