package com.example.kmsquiz.data.DB;

public class QuizDB {
    private int QuizId;
    private String Title;
    private int Q;

    public int getQ() {
        return Q;
    }

    public void setQ(int q) {
        Q = q;
    }

    public int getP() {
        return P;
    }

    public void setP(int p) {
        P = p;
    }

    private int P;


    public int getQuizId() {
        return QuizId;
    }

    public void setQuizId(int quizId) {
        QuizId = quizId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
