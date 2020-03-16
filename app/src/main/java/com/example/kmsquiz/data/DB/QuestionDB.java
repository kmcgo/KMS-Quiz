package com.example.kmsquiz.data.DB;

public class QuestionDB {

    private String Txt;
    private int num;
    private int TotPts;
    private int QuizId;


    public int getQuizId() {
        return QuizId;
    }

    public void setQuizId(int quizId) {
        QuizId = quizId;
    }

    public String getTxt() {
        return Txt;
    }

    public void setTxt(String txt) {
        Txt = txt;
    }

    public int getId() {
        return num;
    }

    public void setId(int num) {
        this.num = num;
    }

    public int getTotPts() {
        return TotPts;
    }

    public void setTotPts(int pts) {
        TotPts = pts;
    }

}
