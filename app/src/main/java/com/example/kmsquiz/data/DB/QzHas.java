package com.example.kmsquiz.data.DB;

public class QzHas {

    private int QuesID;
    private int QuizId;
    private int AnsId;
    private int Correct;

    public int getQuesID() {
        return QuesID;
    }

    public void setQuesID(int quesID) {
        QuesID = quesID;
    }

    public int getQuizId() {
        return QuizId;
    }

    public void setQuizId(int quizId) {
        QuizId = quizId;
    }

    public int getAnsId() {
        return AnsId;
    }

    public void setAnsId(int ansId) {
        AnsId = ansId;
    }

    public int getCorrect() {
        return Correct;
    }

    public void setCorrect(int correct) {
        Correct = correct;
    }
}
